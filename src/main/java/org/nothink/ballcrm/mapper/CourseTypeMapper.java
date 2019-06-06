package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.CourseTypeEntity;

public interface CourseTypeMapper {
    int deleteByPrimaryKey(Integer pkid);

    int insert(CourseTypeEntity record);

    int insertSelective(CourseTypeEntity record);

    CourseTypeEntity selectByPrimaryKey(Integer pkid);

    int updateByPrimaryKeySelective(CourseTypeEntity record);

    int updateByPrimaryKey(CourseTypeEntity record);
}