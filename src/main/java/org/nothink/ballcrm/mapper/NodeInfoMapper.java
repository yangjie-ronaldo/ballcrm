package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.NodeInfoEntity;

public interface NodeInfoMapper {
    int deleteByPrimaryKey(Integer nid);

    int insert(NodeInfoEntity record);

    int insertSelective(NodeInfoEntity record);

    NodeInfoEntity selectByPrimaryKey(Integer nid);

    int updateByPrimaryKeySelective(NodeInfoEntity record);

    int updateByPrimaryKey(NodeInfoEntity record);
}