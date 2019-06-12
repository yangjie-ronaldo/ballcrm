package org.nothink.ballcrm.service;

import org.nothink.ballcrm.entity.CodeDefEntity;
import org.nothink.ballcrm.entity.EmpInfoEntity;
import org.nothink.ballcrm.entity.NodeInfoEntity;
import org.nothink.ballcrm.mapper.CommonMapper;
import org.nothink.ballcrm.mapper.EmpInfoMapper;
import org.nothink.ballcrm.mapper.NodeInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CacheService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    CommonMapper commonMapper;
    @Autowired
    EmpInfoMapper eMapper;
    @Autowired
    NodeInfoMapper nMapper;

    @Cacheable(cacheNames="mainCache",key="'CodeCache'")
    public Map<String, String> CodeDefCache() {
        logger.info("刷新代码缓存...");
        HashMap<String, String> map = new HashMap<>();
        List<CodeDefEntity> list = commonMapper.getCodeDef();
        if (list != null) {
            for (CodeDefEntity cd : list) {
                map.put(cd.getCode(),cd.getDefinition());
            }
        }
        return map;
    }
    @CachePut(cacheNames="mainCache",key="'CodeCache'")
    public Map<String, String> freshCodeDefCache() {
        return this.CodeDefCache();
    }

    @Cacheable(cacheNames="mainCache",key="'EmpCache'")
    public Map<Integer, String> EmpCache() {
        logger.info("刷新员工缓存...");
        HashMap<Integer, String> map = new HashMap<>();
        List<EmpInfoEntity> list = eMapper.getEmpList(new EmpInfoEntity());
        if (list != null) {
            for (EmpInfoEntity e : list) {
                map.put(e.getEid(),e.getName());
            }
        }
        return map;
    }
    @CachePut(cacheNames="mainCache",key="'EmpCache'")
    public Map<Integer, String> freshEmpCache() {
        return this.EmpCache();
    }


    @Cacheable(cacheNames = "mainCache",key="'NodeCache'")
    public Map<Integer,String> NodeCache() {
        logger.info("刷新机构缓存...");
        Map<Integer,String> map=new HashMap<>();
        List<NodeInfoEntity> list=nMapper.getNodeList();
        if (list!=null){
            for (NodeInfoEntity n:list){
                map.put(n.getNid(),n.getName());
            }
        }
        return map;
    }
    @CachePut(cacheNames = "mainCache",key="'NodeCache'")
    public Map<Integer,String> FreshNodeCache() {
        return this.NodeCache();
    }
}
