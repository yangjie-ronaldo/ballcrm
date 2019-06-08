package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.CourseScheduleEntity;

import java.util.List;

public interface CourseScheduleMapper {

    List<CourseScheduleEntity> getNotifyScheduleList(CourseScheduleEntity cs);

    List<CourseScheduleEntity> getCourseScheduleList(CourseScheduleEntity cs);

    int deleteByPrimaryKey(Integer pkid);

    int insert(CourseScheduleEntity record);

    int insertSelective(CourseScheduleEntity record);

    CourseScheduleEntity selectByPrimaryKey(Integer pkid);

    int updateByPrimaryKeySelective(CourseScheduleEntity record);

    int updateByPrimaryKey(CourseScheduleEntity record);
}