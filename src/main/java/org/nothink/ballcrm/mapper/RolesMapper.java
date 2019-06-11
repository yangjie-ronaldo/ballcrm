package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.RolesEntity;

import java.util.List;

public interface RolesMapper {
    List<RolesEntity> getAllRoles();

    int deleteByPrimaryKey(String role);

    int insert(RolesEntity record);

    int insertSelective(RolesEntity record);

    RolesEntity selectByPrimaryKey(String role);

    int updateByPrimaryKeySelective(RolesEntity record);

    int updateByPrimaryKey(RolesEntity record);
}