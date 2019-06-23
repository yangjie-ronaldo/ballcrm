package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.StuEntity;
import org.nothink.ballcrm.entity.StuTransCriteria;

import java.util.List;

public interface StuMapper {
    List<StuEntity> getStuList(StuEntity criteria);

    List<StuEntity> getStuTransList(StuTransCriteria criteria);

    int deleteByPrimaryKey(Integer sid);

    int insert(StuEntity record);

    int insertSelectiveAndGetKey(StuEntity record);

    StuEntity selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(StuEntity record);

    int updateByPrimaryKey(StuEntity record);

}
