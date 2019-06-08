package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.LoginTokenEntity;

public interface LoginTokenMapper {


    int deleteByPrimaryKey(Integer eid);

    int insert(LoginTokenEntity record);

    int insertSelective(LoginTokenEntity record);

    LoginTokenEntity selectByPrimaryKey(Integer eid);
    LoginTokenEntity selectByToken(String token);


    int updateByPrimaryKeySelective(LoginTokenEntity record);

    int updateByPrimaryKey(LoginTokenEntity record);
}