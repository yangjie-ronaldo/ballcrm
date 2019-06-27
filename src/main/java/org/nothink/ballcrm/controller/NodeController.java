package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.CodeDefEntity;
import org.nothink.ballcrm.entity.CommonException;
import org.nothink.ballcrm.service.CacheService;
import org.nothink.ballcrm.service.NodeInfoService;
import org.nothink.ballcrm.util.ComUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class NodeController {

    @Autowired
    NodeInfoService nService;
    @Autowired
    CacheService cacheService;

    @GetMapping("/node")
    @ResponseBody
    public Map getNodeList(){
        return nService.getNodeList();
    }

    @GetMapping("/codedef")
    @ResponseBody
    public Map getCodeDef(@RequestParam("category") String category){
        Map<String, List<CodeDefEntity>> codemap = cacheService.StandardCode();
        List<CodeDefEntity> list=codemap.get(category);
        if (list==null){
            list=new ArrayList<>();
        }
        return ComUtils.getResp(20000,"查询成功",list);
    }

}
