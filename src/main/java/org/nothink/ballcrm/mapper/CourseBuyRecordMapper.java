package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.CourseBuyRecordEntity;

public interface CourseBuyRecordMapper {
    int deleteByPrimaryKey(Integer pkid);

    int insert(CourseBuyRecordEntity record);

    int insertSelective(CourseBuyRecordEntity record);

    CourseBuyRecordEntity selectByPrimaryKey(Integer pkid);

    int updateByPrimaryKeySelective(CourseBuyRecordEntity record);

    int updateByPrimaryKey(CourseBuyRecordEntity record);
}