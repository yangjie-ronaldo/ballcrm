package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.StuStatusEntity;

import java.util.List;

public interface StuStatusMapper {
    List<StuStatusEntity> getStuStatusListBySid(Integer sid);

    int deleteByPrimaryKey(Integer pkid);

    int insert(StuStatusEntity record);

    int insertSelective(StuStatusEntity record);

    StuStatusEntity selectByPrimaryKey(Integer pkid);

    int updateByPrimaryKeySelective(StuStatusEntity record);

    int updateByPrimaryKey(StuStatusEntity record);

}