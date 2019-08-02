package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.Statistics;
import org.nothink.ballcrm.entity.StatisticsCriteria;
import org.nothink.ballcrm.entity.StuEntity;

import java.util.List;

public interface StatisticMapper {
    //查询按员工统计的 新增学员数量
    List<Statistics> getNewStuNum(StatisticsCriteria c);
    //明细
    List<StuEntity> getNewStuNumDetails(StatisticsCriteria c);

    //查询按员工统计的 体验课约课数
    List<Statistics> getDemoScheduleNum(StatisticsCriteria c);
    //明细
    List<StuEntity> getDemoScheduleNumDetails(StatisticsCriteria c);

    //查询按员工统计的 体验类课签到数
    List<Statistics> getDemoSignNum(StatisticsCriteria c);
    //明细
    List<StuEntity> getDemoSignNumDetails(StatisticsCriteria c);

    //查询按员工统计的 体验课关单数   （这个demo家长由谁去勾兑的，我关的单）
    List<Statistics> getDemoCloseNum(StatisticsCriteria c);
    //明细
    List<StuEntity> getDemoCloseNumDetails(StatisticsCriteria c);

    //查询学员按状态的统计数量
    List<Statistics> getStuStatusNum(StatisticsCriteria c);
    //明细
    List<StuEntity> getStuStatusNumDetails(StatisticsCriteria c);

    //查询按课程分类的统计数量
    List<Statistics> getCourseCloseNum(StatisticsCriteria c);
    //明细
    List<StuEntity> getCourseCloseNumDetails(StatisticsCriteria c);

    //查询按课程分类的老师配合数量
    List<Statistics> getCourseCloseTeacherNum(StatisticsCriteria c);
    //明细
    List<StuEntity> getCourseCloseTeacherNumDetails(StatisticsCriteria c);

//
//    //查询按员工统计的 198成交数
//    List<Statistics> getBuy198Num(StatisticsCriteria c);
//    //查询名下购198学员数
//    List<Statistics> getMyStu198Num(StatisticsCriteria c);
}
