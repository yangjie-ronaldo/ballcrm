package org.nothink.ballcrm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.mapper.CourseScheduleMapper;
import org.nothink.ballcrm.mapper.StuCourseMapper;
import org.nothink.ballcrm.mapper.StuFamilyMapper;
import org.nothink.ballcrm.util.CodeDef;
import org.nothink.ballcrm.util.ComUtils;
import org.nothink.ballcrm.util.DateUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CourseScheduleService {
    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    StuService stuService;
    @Autowired
    CourseScheduleMapper csMapper;
    @Autowired
    StuCourseMapper stuCoureMapper;
    @Autowired
    CacheService cache;
    @Autowired
    StuFamilyMapper sfMapper;

    /**
     * 学员约课
     *
     * @param book
     * @return
     */
    @Transactional
    public Map bookCourse(CourseScheduleEntity book) {
        int sid = book.getSid();
        StuEntity stu = stuService.findById(sid);
        if (stu==null)
            return ComUtils.getResp(40008,"无学员信息",null);
        if (CodeDef.STU_BOOKED.equals(stu.getStatus())) {
            return ComUtils.getResp(40008,"已有预约课程",null);
        }

        //新增约课记录
        book.setCreateDate(new Date());
        book.setNotifyStatus(CodeDef.HANDLE_WAITING);
        book.setSignStatus(CodeDef.SIGN_WAITING);
        book.setTraceStatus(CodeDef.HANDLE_WAITING);
        System.out.println(book);
        int i = csMapper.insertSelective(book);

        //学员状态改变
        stuService.updateStuStatus(stu, CodeDef.STU_BOOKED, "");
        return ComUtils.getResp(20000,"已成功约课",null);
    }

    /**
     * 学员上课流水列表
     */
    public Map CourseScheduleList(CourseScheduleEntity cs) {
        Page p = PageHelper.startPage(cs.getCurrentPage(), cs.getPageSize());

        //执行查询
        List<CourseScheduleEntity> list = csMapper.getCourseScheduleList(cs);
        PagedResult<CourseScheduleEntity> result = new PagedResult<>(cs.getCurrentPage(), cs.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list != null)
            for (CourseScheduleEntity item : list)
                transCode(item);
        result.setItems(list);
        return ComUtils.getResp(20000,"查询成功",result);
    }

    /**
     * 明日上课提醒列表
     */
    public Map notifyScheduleList(CourseScheduleEntity cs) {
        Page p = PageHelper.startPage(cs.getCurrentPage(), cs.getPageSize());
        List<CourseScheduleEntity> list = csMapper.getNotifyScheduleList(cs);
        PagedResult<CourseScheduleEntity> result = new PagedResult<>(cs.getCurrentPage(), cs.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list != null)
            for (CourseScheduleEntity item : list)
                transCode(item);
        result.setItems(list);
        return ComUtils.getResp(20000,"查询成功",result);

    }

    /**
     * 本日上课课程列表
     */
    public Map scheduleListToday(CourseScheduleEntity cs) {
        Page p = PageHelper.startPage(cs.getCurrentPage(), cs.getPageSize());
        //如果没有选时间条件，则设为查当日
        if (cs.getStartDate()==null && cs.getEndDate()==null){
            cs.setBookingDate(DateUtils.getToday());
        }
        List<CourseScheduleEntity> list = csMapper.getScheduleToday(cs);
        PagedResult<CourseScheduleEntity> result = new PagedResult<>(cs.getCurrentPage(), cs.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list != null)
            for (CourseScheduleEntity item : list)
                transCode(item);
        result.setItems(list);
        return ComUtils.getResp(20000,"查询成功",result);

    }

    /**
     * 明日上课通知处理
     *
     * @param cs
     */
    @Transactional
    public Map handleScheduleNotify(CourseScheduleEntity cs) {
        System.out.println(cs);
        CourseScheduleEntity relCs = csMapper.selectByPrimaryKey(cs.getPkid());
        if (relCs == null)
            return ComUtils.getResp(40008,"无上课信息",null);
        relCs.setNotifyStatus(CodeDef.HANDLED);
        relCs.setNotifyNote(cs.getNotifyNote());
        //学生情况 改期
        if (CodeDef.SIGN_CHANGE.equals(cs.getSignStatus())) {
            // 如果是改期，修改学员状态
            StuEntity stu = stuService.findById(cs.getSid());
            stuService.updateStuStatus(stu, CodeDef.STU_WAITING, "因个人原因，课程改期");
            //本条课程也不再追踪
            relCs.setSignStatus(CodeDef.SIGN_CHANGE);
            relCs.setTraceStatus(CodeDef.HANDLED);
            relCs.setTraceNote("改期");
        }
        int r = csMapper.updateByPrimaryKeySelective(relCs);
        if (r>0)
            return ComUtils.getResp(20000,"处理成功",null);
        else
            return ComUtils.getResp(40008,"处理出错",null);
    }

    /**
     * 本日上课处理
     *
     * @param criteria
     */
    @Transactional
    public Map handleScheduleToday(CourseScheduleEntity criteria) {
        CourseScheduleEntity relCs = csMapper.selectByPrimaryKey(criteria.getPkid());
        if (relCs == null)
            return ComUtils.getResp(40008,"无课程信息",null);

        //如果是198课，查询有无维护家庭信息，没有则不能处理
        if (relCs.getCourseTypeId()==2){
            StuFamilyEntity sf=sfMapper.selectByPrimaryKey(relCs.getSid());
            if (sf==null || StringUtils.isEmpty(sf.getPayWill())){
                return ComUtils.getResp(40008,"未完成家庭信息填写，不能处理本课程追踪",null);
            }
        }

        relCs.setTraceStatus(CodeDef.HANDLED);
        relCs.setTraceNote(criteria.getTraceNote());

        if (CodeDef.SIGN_WAITING.equals(relCs.getSignStatus()) && CodeDef.SIGN_TRUANCY.equals(criteria.getSignStatus())){
            // 学员未签到，是旷课情况 处理
            relCs.setSignStatus(CodeDef.SIGN_TRUANCY);
            StuEntity stu = stuService.findById(criteria.getSid());
            if (stu==null){
                throw new CommonException(40008,"无学员信息");
            }
            stuService.updateStuStatus(stu,CodeDef.STU_TRUANCY,"爽约未上课，及时跟进");
        }
        int r = csMapper.updateByPrimaryKeySelective(relCs);
        if (r>0)
            return ComUtils.getResp(20000,"处理成功",null);
        else
            return ComUtils.getResp(40008,"处理失败",null);
    }

    /**
     * 签到
     * @param cs
     */
    @Transactional
    public Map signIn(CourseScheduleEntity cs) {
        if (cs.getSid()==null){
            return ComUtils.getResp(40008,"无学员编号",null);
        }
        if (cs.getCourseTypeId()==null){
            return ComUtils.getResp(40008,"无课程编号",null);
        }
        if (cs.getPkid()==null){
            return ComUtils.getResp(40008,"无约课信息",null);
        }
        // 本次课签到
        CourseScheduleEntity course = csMapper.selectByPrimaryKey(cs.getPkid());
        if (course == null)
            return ComUtils.getResp(40008,"未找到上课信息",null);
        // 更新此次上课签到状态
        course.setSignStatus(CodeDef.SIGN_OK);
        course.setCloseEid(cs.getCloseEid());  //聊天人
        course.setTeachEid(cs.getTeachEid());  //上课老师
        csMapper.updateByPrimaryKeySelective(course);

        // 加入流程：如果签的是198小课包，则查询有无ishow课激活，若无，则插入ishow课
        if (course.getCourseTypeId()==2){
            StuCourseEntity sc=new StuCourseEntity();
            sc.setSid(course.getSid());
            sc.setCourseTypeId(4);  //ishow课
            StuCourseEntity choose = stuCoureMapper.getStuCourseSelective(sc);
            if (choose==null || choose.getSid()==null){
                //无ishow课，新增ishow课购买纪录
                sc.setSid(course.getSid());
                sc.setNum(30);
                sc.setCourseTypeId(4);
                sc.setCreateDate(new Date());
                //结束时间为30天后
                sc.setEndDate(DateUtils.addDate(new Date(),0,0,30,0,0,0,0));
                sc.setEid(course.getEid());  //订课的人
                stuCoureMapper.insertSelective(sc);
            }
        }

        // 减此学员签到课的课时信息
        StuCourseEntity c = new StuCourseEntity();
        c.setSid(cs.getSid());
        c.setCourseTypeId(cs.getCourseTypeId());
        StuCourseEntity sc = stuCoureMapper.getStuCourseSelective(c);
        System.out.println("买的课："+sc);
        if (sc == null){
            return ComUtils.getResp(40008,"学员已无课时信息！",null);
        }
        sc.setNum(sc.getNum() - 1);
        sc.setUpdateDate(new Date()); //更新时间
        stuCoureMapper.updateByPrimaryKeySelective(sc);

        //更新学员状态 如果课时已为0了 则学员上完课了
        StuEntity stu = stuService.findById(cs.getSid());
        if (sc.getNum() > 0) {
            //还有课
            stuService.updateStuStatus(stu, CodeDef.STU_COURSE_SIGNED, "正常上课中");
        } else {
            stuService.updateStuStatus(stu, CodeDef.STU_COURSE_OVER, "课程完成！及时跟进营销！");
        }
        return ComUtils.getResp(20000,"成功签到",null);
    }

    /**
     * 撤销签到
     * @param cs
     * @return
     */
    @Transactional
    public Map SignInReverse(CourseScheduleEntity cs){
        if (cs.getSid()==null){
            return ComUtils.getResp(40008,"无学员编号",null);
        }
        if (cs.getCourseTypeId()==null){
            return ComUtils.getResp(40008,"无课程编号",null);
        }
        if (cs.getPkid()==null){
            return ComUtils.getResp(40008,"无约课信息",null);
        }

        // 本次约课签到撤销
        CourseScheduleEntity course = csMapper.selectByPrimaryKey(cs.getPkid());
        if (course == null)
            return ComUtils.getResp(40008,"未找到上课信息",null);
        // 更新此次上课签到状态
        course.setSignStatus(CodeDef.SIGN_WAITING); //改为等待上课
        course.setCloseEid(0);  //撤销课程关单人
        course.setTeachEid(0);  //撤销课程上课人
        csMapper.updateByPrimaryKeySelective(course);

        // 恢复学员签到课的课时信息
        StuCourseEntity c = new StuCourseEntity();
        c.setSid(cs.getSid());
        c.setCourseTypeId(cs.getCourseTypeId());
        StuCourseEntity sc = stuCoureMapper.getStuCourseSelective(c);
        if (sc == null){
            return ComUtils.getResp(40008,"学员无此购买课程信息",null);
        }
        //看课程类型与数量信息，分别处理
        if (sc.getNum()==2 && sc.getCourseTypeId()==2){
            //是198课且签到的是第一节，则反删除加的ishow课
            StuCourseEntity ishow=new StuCourseEntity();
            ishow.setSid(cs.getSid());
            ishow.setCourseTypeId(4);
            ishow=stuCoureMapper.getStuCourseSelective(ishow);
            if (ishow!=null && ishow.getPkid()!=null){
                stuCoureMapper.deleteByPrimaryKey(ishow.getPkid());
            }
        }
        //恢复此课程的一节课程
        sc.setNum(sc.getNum() + 1);
        stuCoureMapper.updateByPrimaryKeySelective(sc);

        //更新学员状态 更新为待上课
        StuEntity stu = stuService.findById(cs.getSid());
        stuService.updateStuStatus(stu, CodeDef.STU_BOOKED, "撤销签到，等待签到上课");
        return ComUtils.getResp(20000,"撤销签到成功",null);
    }


    private void transCode(CourseScheduleEntity cs) {
        if (cs != null) {
            cs.setNotifyStatusDef(cache.CodeDefCache().get(cs.getNotifyStatus()));
            cs.setSignStatusDef(cache.CodeDefCache().get(cs.getSignStatus()));
            cs.setTraceStatusDef(cache.CodeDefCache().get(cs.getTraceStatus()));
            // 上课老师
            cs.setTeacheName(cache.EmpCache().get(cs.getTeachEid()));
            // 课程名
            cs.setCourseTypeName(cache.CourseCache().get(cs.getCourseTypeId()));
        }
    }
}