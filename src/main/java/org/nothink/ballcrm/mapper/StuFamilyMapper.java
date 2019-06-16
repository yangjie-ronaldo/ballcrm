package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.StuFamilyEntity;

public interface StuFamilyMapper {
    int deleteByPrimaryKey(Integer sid);

    int insert(StuFamilyEntity record);

    int insertSelective(StuFamilyEntity record);

    StuFamilyEntity selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(StuFamilyEntity record);

    int updateByPrimaryKey(StuFamilyEntity record);
}