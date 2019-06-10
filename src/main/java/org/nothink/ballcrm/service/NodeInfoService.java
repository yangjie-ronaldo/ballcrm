package org.nothink.ballcrm.service;

import org.nothink.ballcrm.mapper.NodeInfoMapper;
import org.nothink.ballcrm.util.ComUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NodeInfoService {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    NodeInfoMapper nodeMapper;

    // 获取门店列表
    public Map getNodeList(){
        List list=nodeMapper.getNodeList();
        return ComUtils.getResp(20000,"查询成功",list);
    }
}
