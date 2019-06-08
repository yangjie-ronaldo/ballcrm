package org.nothink.ballcrm.service;

import org.nothink.ballcrm.mapper.NodeInfoMapper;
import org.nothink.ballcrm.util.ComUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Service
public class NodeInfoService {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    NodeInfoMapper nodeMapper;

    // 获取门店列表
    public List getNodeList(){
        List list=nodeMapper.getNodeList();
        return list;
    }
}
