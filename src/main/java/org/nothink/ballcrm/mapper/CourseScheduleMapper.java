package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.CourseScheduleEntity;

import java.util.List;

public interface CourseScheduleMapper {

    // 查本日上课情况
    List<CourseScheduleEntity> getScheduleToday(CourseScheduleEntity cs);

    // 查明日通知的课程
    List<CourseScheduleEntity> getNotifyScheduleList(CourseScheduleEntity cs);

    // 按sid查学员的所有课程
    List<CourseScheduleEntity> getCourseScheduleList(CourseScheduleEntity cs);

    int deleteByPrimaryKey(Integer pkid);

    int insert(CourseScheduleEntity record);

    int insertSelective(CourseScheduleEntity record);

    CourseScheduleEntity selectByPrimaryKey(Integer pkid);

    int updateByPrimaryKeySelective(CourseScheduleEntity record);

    int updateByPrimaryKey(CourseScheduleEntity record);
}