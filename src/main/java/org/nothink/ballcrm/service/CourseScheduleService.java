package org.nothink.ballcrm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.nothink.ballcrm.entity.CourseScheduleEntity;
import org.nothink.ballcrm.entity.PagedResult;
import org.nothink.ballcrm.entity.StuCourseEntity;
import org.nothink.ballcrm.entity.StuEntity;
import org.nothink.ballcrm.mapper.CourseScheduleMapper;
import org.nothink.ballcrm.mapper.StuCourseMapper;
import org.nothink.ballcrm.util.CodeDef;
import org.nothink.ballcrm.util.ComUtils;
import org.nothink.ballcrm.util.DateUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
        //设为查当日
        cs.setBookingDate(DateUtils.getToday());
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
        //学生情况，只能处理成 待上课 和 改期
        relCs.setSignStatus(cs.getSignStatus());
        if (CodeDef.SIGN_CHANGE.equals(cs.getSignStatus())) {
            // 如果是改期，修改学员状态
            StuEntity stu = stuService.findById(cs.getSid());
            stuService.updateStuStatus(stu, CodeDef.STU_WAITING, "因个人原因，课程改期");
            //本条课程也不再追踪
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
     * @param cs
     */
    @Transactional
    public Map handleScheduleToday(CourseScheduleEntity cs) {
        CourseScheduleEntity relCs = csMapper.selectByPrimaryKey(cs.getPkid());
        if (relCs == null)
            return ComUtils.getResp(40008,"无上课信息",null);
        relCs.setTraceStatus(CodeDef.HANDLED);
        relCs.setTraceNote(cs.getTraceNote());

        if (CodeDef.SIGN_WAITING.equals(relCs.getSignStatus()) && CodeDef.SIGN_TRUANCY.equals(cs.getSignStatus())){
            // 学员未签到，是旷课情况处理
            relCs.setSignStatus(CodeDef.SIGN_TRUANCY);
            StuEntity stu = stuService.findById(cs.getSid());
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
        // 本次课签到
        CourseScheduleEntity course = csMapper.selectByPrimaryKey(cs.getPkid());
        if (course == null)
            return ComUtils.getResp(40008,"未找到上课信息",null);
        // 更新此次上课签到状态
        course.setSignStatus(CodeDef.SIGN_OK);
        csMapper.updateByPrimaryKeySelective(course);

        // 减此学员的课时信息
        StuCourseEntity c = new StuCourseEntity();
        c.setSid(cs.getSid());
        c.setCourseTypeId(cs.getCourseTypeId());
        StuCourseEntity sc = stuCoureMapper.getStuCourseAvailable(c);
        System.out.println("买的课："+sc);
        if (sc == null){
            return ComUtils.getResp(40008,"学员已无课时信息！",null);
        }
        sc.setNum(sc.getNum() - 1);
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


    private void transCode(CourseScheduleEntity cs) {
        if (cs != null) {
            cs.setNotifyStatusDef(cache.CodeDefCache().get(cs.getNotifyStatus()));
            cs.setSignStatusDef(cache.CodeDefCache().get(cs.getSignStatus()));
            cs.setTraceStatusDef(cache.CodeDefCache().get(cs.getTraceStatus()));
            // 上课老师
            cs.setTeacheName(cache.EmpCache().get(cs.getTeachEid()));
        }
    }

}
