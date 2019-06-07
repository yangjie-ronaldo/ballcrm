package org.nothink.ballcrm.service;

import org.nothink.ballcrm.entity.CodeDefEntity;
import org.nothink.ballcrm.entity.EmpInfoEntity;
import org.nothink.ballcrm.mapper.CommonMapper;
import org.nothink.ballcrm.mapper.EmpInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CacheService {

    @Autowired
    CommonMapper commonMapper;
    @Autowired
    EmpInfoMapper eMapper;

    @Cacheable(cacheNames="mainCache",key="#root.methodName")
    public Map<String, String> CodeDefCache() {
        HashMap<String, String> map = new HashMap<>();
        List<CodeDefEntity> list = commonMapper.getCodeDef();
        if (list != null) {
            for (CodeDefEntity cd : list) {
                map.put(cd.getCode(),cd.getDefinition());
            }
        }
        return map;
    }

    @Cacheable(cacheNames="mainCache",key="#root.methodName")
    public Map<Integer, String> EmpCache() {
        HashMap<Integer, String> map = new HashMap<>();
        List<EmpInfoEntity> list = eMapper.getEmpList(new EmpInfoEntity());
        if (list != null) {
            for (EmpInfoEntity e : list) {
                map.put(e.getEid(),e.getName());
            }
        }
        return map;
    }
}
