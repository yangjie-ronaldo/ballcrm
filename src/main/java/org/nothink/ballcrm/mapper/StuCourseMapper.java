package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.StuCourseEntity;

import java.util.List;

public interface StuCourseMapper {

    StuCourseEntity getStuCourseSelective(StuCourseEntity sc);

    StuCourseEntity getStuCourseAvailable(StuCourseEntity sc);

    List<StuCourseEntity> getStuCourseListBySid(Integer sid);

    int deleteByPrimaryKey(Integer pkid);

    int insert(StuCourseEntity record);

    int insertSelective(StuCourseEntity record);

    StuCourseEntity selectByPrimaryKey(Integer pkid);

    int updateByPrimaryKeySelective(StuCourseEntity record);

    int updateByPrimaryKey(StuCourseEntity record);
}