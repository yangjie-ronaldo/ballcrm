package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.EmpRoleRelEntity;

import java.util.List;

public interface EmpRoleRelMapper {

    List<EmpRoleRelEntity> selectByEid(Integer eid);

    int deleteByPrimaryKey(EmpRoleRelEntity key);

    int deleteByEid(Integer eid);

    int insert(EmpRoleRelEntity record);

    int insertSelective(EmpRoleRelEntity record);
}