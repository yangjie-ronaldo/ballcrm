package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.CourseTypeEntity;

import java.util.List;

public interface CourseTypeMapper {
    List<CourseTypeEntity> getCourseForStatistics();
    List<CourseTypeEntity> getCourse();

    List<CourseTypeEntity> getCourseByCriteria(CourseTypeEntity criteria);


    List<CourseTypeEntity> getCourseForBuy();

    int deleteByPrimaryKey(Integer pkid);

    int insert(CourseTypeEntity record);

    int insertSelective(CourseTypeEntity record);

    CourseTypeEntity selectByPrimaryKey(Integer pkid);

    int updateByPrimaryKeySelective(CourseTypeEntity record);

    int updateByPrimaryKey(CourseTypeEntity record);
}