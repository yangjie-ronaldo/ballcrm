package org.nothink.ballcrm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.mapper.CourseScheduleMapper;
import org.nothink.ballcrm.mapper.CourseTypeMapper;
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
    CourseTypeMapper courseTypeMapper;
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
        Date bookingDate=book.getBookingDate();
        StuEntity stu = stuService.findById(sid);
        if (stu==null)
            return ComUtils.getResp(40008,"无学员信息",null);
        if (stu.getType().equals(CodeDef.TYPE_HOUXUAN)){
            return ComUtils.getResp(40008,"学员已被放弃，不能约课，请购买课程激活",null);
        }
        //判断约课的情况
        if (book.getCourseTypeId()==null || bookingDate==null){
            return ComUtils.getResp(40008,"未选预约课程或日期",null);
        }
        // 查询是否有待上课的课程
        CourseScheduleEntity courseCriteria=new CourseScheduleEntity();
        courseCriteria.setSid(sid);
        List<CourseScheduleEntity> courseScheduleTodo = csMapper.getCourseScheduleTodo(courseCriteria);
        if (courseScheduleTodo!=null && courseScheduleTodo.size()>=1){
            return ComUtils.getResp(40008,"已有预约课程",null);
        }

        //判断约课时间是否在学员此课程有效期内
        StuCourseEntity scCriteria=new StuCourseEntity();
        scCriteria.setSid(sid);
        scCriteria.setCourseTypeId(book.getCourseTypeId());
        StuCourseEntity stuCourse = stuCoureMapper.getStuCourseSelective(scCriteria);
        if (stuCourse==null){
            return ComUtils.getResp(40008,"无此课程的购买信息",null);
        }
        Date startDate=DateUtils.getDayBeginTime(stuCourse.getCreateDate());
        Date endDate=DateUtils.getDayEndTime(stuCourse.getEndDate());
        if (DateUtils.isBefore(bookingDate,startDate) || DateUtils.isBefore(endDate,bookingDate)){
            return ComUtils.getResp(40008,"预约时间不在购买课程的有效期内",null);
        }

        //可以约课 新增约课记录
        book.setCreateDate(new Date());
        book.setNotifyStatus(CodeDef.HANDLE_WAITING);
        book.setSignStatus(CodeDef.SIGN_WAITING);
        book.setTraceStatus(CodeDef.HANDLE_WAITING);
        int i = csMapper.insertSelective(book);

        //学员状态改变 根据学员的类型来确认约了课是什么状态
        if (CodeDef.TYPE_PROTENTIAL.equals(stu.getType())){
            //体验课学员
            stuService.updateStuStatus(stu, CodeDef.STU_BOOKED, "预约体验课");
        } else if (CodeDef.TYPE_SALE.equals(stu.getType())){
            //营销课学员
            stuService.updateStuStatus(stu,CodeDef.STU_SALE_STUDY,"预约营销课");
        } else {
            //TODO 正课约课状态
        }

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
        if (cs.getEndDate()!=null){
            //如果有结束时间，设一下结束时间为当天结束
            cs.setEndDate(DateUtils.getDayEndTime(cs.getEndDate()));
        }
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

        if (cs.getStartDate()==null && cs.getEndDate()==null){
            //如果没有选时间条件，则设为查当日
            cs.setBookingDate(DateUtils.getToday());
        } else {
            //否则如果选结束时间，设一下结束时间为当天结束
            if (cs.getEndDate()!=null){
                cs.setEndDate(DateUtils.getDayEndTime(cs.getEndDate()));
            }
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
        CourseScheduleEntity relCs = csMapper.selectByPrimaryKey(cs.getPkid());
        if (relCs == null)
            return ComUtils.getResp(40008,"无上课信息",null);
        relCs.setNotifyStatus(CodeDef.HANDLED);
        relCs.setNotifyNote(cs.getNotifyNote());
        //获取学员
        StuEntity stu = stuService.findById(cs.getSid());

        //学生情况 改期
        if (CodeDef.SIGN_CHANGE.equals(cs.getSignStatus())) {
            // 如果是改期，根据学员类型修改学员状态
            if (CodeDef.TYPE_PROTENTIAL.equals(stu.getType())){
                //体验课学员
                stuService.updateStuStatus(stu, CodeDef.STU_BOOKED_PAUSE, "体验课改期");
            } else if (CodeDef.TYPE_SALE.equals(stu.getType())){
                //营销课学员
                stuService.updateStuStatus(stu,CodeDef.STU_SALE_PAUSE,"营销课改期");
            } else{
                //TODO 缺正课改期
            }

            //本条课程也不再追踪
            relCs.setSignStatus(CodeDef.SIGN_CHANGE);
            relCs.setTraceStatus(CodeDef.HANDLED);
            relCs.setTraceNote("已改期");
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

        //特殊逻辑 如果是营销课，查询是否已维护了家庭信息，若没有则不能处理
        CourseTypeEntity course = courseTypeMapper.selectByPrimaryKey(relCs.getCourseTypeId());
        if (course!=null && course.getPhaseId()==3){  //3是营销课
            StuFamilyEntity sf=sfMapper.selectByPrimaryKey(relCs.getSid());
            if (sf==null || StringUtils.isEmpty(sf.getEducationIdea())){
                return ComUtils.getResp(40008,"未完成家庭信息填写，不能处理本课程追踪",null);
            }
        }

        relCs.setTraceStatus(CodeDef.HANDLED);
        relCs.setTraceNote(criteria.getTraceNote());

        if (CodeDef.SIGN_WAITING.equals(relCs.getSignStatus()) && CodeDef.SIGN_TRUANCY.equals(criteria.getSignStatus())){
            // 学员未签到，是 旷课情况 的处理
            relCs.setSignStatus(CodeDef.SIGN_TRUANCY);
            StuEntity stu = stuService.findById(criteria.getSid());
            if (stu==null){
                throw new CommonException(40008,"无学员信息");
            }
            // 根据学员类型置不同状态
            if (CodeDef.TYPE_PROTENTIAL.equals(stu.getType())){
                //体验课学员
                stuService.updateStuStatus(stu, CodeDef.STU_BOOKED_PAUSE, "体验课旷课");
            } else if (CodeDef.TYPE_SALE.equals(stu.getType())){
                //营销课学员
                stuService.updateStuStatus(stu,CodeDef.STU_SALE_PAUSE,"营销课旷课");
            } else {
                //TODO 正课旷课的情况
            }

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
            return ComUtils.getResp(40008,"未找到课程信息",null);
        // 更新此次上课签到状态
        course.setSignStatus(CodeDef.SIGN_OK);
        course.setCloseEid(cs.getCloseEid());  //聊天人
        course.setTeachEid(cs.getTeachEid());  //上课老师
        csMapper.updateByPrimaryKeySelective(course);

        /*暂废弃加入流程：如果签的是198小课包，则查询有无ishow课激活，若无，则插入ishow课
        if (course.getCourseTypeId()==2){
            StuCourseEntity sc=new StuCourseEntity();
            sc.setSid(course.getSid());
            sc.setCourseTypeId(4);  //ishow课
            StuCourseEntity choose = stuCoureMapper.getStuCourseSelective(sc);
            if (choose==null || choose.getSid()==null){
                //无ishow课，新增ishow课购买纪录
                sc.setSid(course.getSid());
                //不设置ishow课的数量
                sc.setNum(null);
                sc.setCourseTypeId(4);
                sc.setCreateDate(new Date());
                //结束时间为30天后
                sc.setEndDate(DateUtils.addDate(new Date(),0,0,30,0,0,0,0));
                sc.setEid(course.getEid());  //订课的人
                stuCoureMapper.insertSelective(sc);
            }
        }*/

        // 签完到的后续处理
        // 1.减此学员签到课的课时信息
        StuCourseEntity c = new StuCourseEntity();
        c.setSid(cs.getSid());
        c.setCourseTypeId(cs.getCourseTypeId());
        StuCourseEntity sc = stuCoureMapper.getStuCourseSelective(c);
        if (sc == null){
            throw new CommonException(40008,"学员无购买课程信息");
        }
        sc.setNum(sc.getNum() - 1);
        sc.setUpdateDate(new Date()); //更新时间
        stuCoureMapper.updateByPrimaryKeySelective(sc);

        // 2.根据学员类型来更新学员状态 如果课时已为0 则学员上完课
        StuEntity stu = stuService.findById(cs.getSid());
        if (sc.getNum() > 0) {
            //还有课 无需处理状态
        } else {
            // 已上完课，改状态
            if (CodeDef.TYPE_PROTENTIAL.equals(stu.getType())){
                //体验课学员
                stuService.updateStuStatus(stu, CodeDef.STU_BOOKED_NODEAL, "上完体验课");
            } else if (CodeDef.TYPE_SALE.equals(stu.getType())){
                //营销课学员
                stuService.updateStuStatus(stu,CodeDef.STU_SALE_NODEAL,"上完营销课");
            } else {
                //TODO 上完正课
            }
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
            throw new CommonException(40008,"学员无购买课程信息");
        }
        //恢复此课程的一节课程
        sc.setNum(sc.getNum() + 1);
        sc.setUpdateDate(new Date());
        stuCoureMapper.updateByPrimaryKeySelective(sc);

        //更新学员状态 更新为待上课
        StuEntity stu = stuService.findById(cs.getSid());
        if (CodeDef.TYPE_PROTENTIAL.equals(stu.getType())){
            //体验课学员
            stuService.updateStuStatus(stu, CodeDef.STU_BOOKED, "撤销体验课签到");
        } else if (CodeDef.TYPE_SALE.equals(stu.getType())){
            //营销课学员
            stuService.updateStuStatus(stu,CodeDef.STU_SALE_STUDY,"撤销营销课签到");
        } else {
            //TODO 正课撤销
        }
        return ComUtils.getResp(20000,"撤销签到成功",null);
    }


    private void transCode(CourseScheduleEntity cs) {
        if (cs != null) {
            cs.setNotifyStatusDef(cache.CodeDefCache().get(cs.getNotifyStatus()));
            cs.setSignStatusDef(cache.CodeDefCache().get(cs.getSignStatus()));
            cs.setTraceStatusDef(cache.CodeDefCache().get(cs.getTraceStatus()));
            // 渠道名
            cs.setChannelName(cache.CodeDefCache().get(cs.getChannel()));
            // 上课老师
            cs.setTeacheName(cache.EmpCache().get(cs.getTeachEid()));
            // 课程名
            cs.setCourseTypeName(cache.CourseCache().get(cs.getCourseTypeId()));

        }
    }
}