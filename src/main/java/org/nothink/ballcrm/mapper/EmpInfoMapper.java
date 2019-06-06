package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.EmpInfoEntity;

import java.util.List;

public interface EmpInfoMapper {
    List<EmpInfoEntity> getEmpList();

    int deleteByPrimaryKey(Integer eid);

    int insert(EmpInfoEntity record);

    int insertSelective(EmpInfoEntity record);

    EmpInfoEntity selectByPrimaryKey(Integer eid);

    int updateByPrimaryKeySelective(EmpInfoEntity record);

    int updateByPrimaryKey(EmpInfoEntity record);
}