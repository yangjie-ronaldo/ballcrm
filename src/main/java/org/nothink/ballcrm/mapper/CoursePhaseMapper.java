package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.CoursePhaseEntity;

public interface CoursePhaseMapper {
    int deleteByPrimaryKey(Integer pkid);

    int insert(CoursePhaseEntity record);

    int insertSelective(CoursePhaseEntity record);

    CoursePhaseEntity selectByPrimaryKey(Integer pkid);

    int updateByPrimaryKeySelective(CoursePhaseEntity record);

    int updateByPrimaryKey(CoursePhaseEntity record);
}