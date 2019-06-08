package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.EmpInfoEntity;

import java.util.List;

public interface EmpInfoMapper {
    EmpInfoEntity getEmpByLoginid(String loginid);

    List<EmpInfoEntity> getEmpList(EmpInfoEntity e);

    int deleteByPrimaryKey(Integer eid);

    int insert(EmpInfoEntity record);

    int insertSelectiveAndGetKey(EmpInfoEntity record);

    EmpInfoEntity selectByPrimaryKey(Integer eid);

    int updateByPrimaryKeySelective(EmpInfoEntity record);

    int updateByPrimaryKey(EmpInfoEntity record);
}