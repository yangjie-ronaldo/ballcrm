package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.service.NodeInfoService;
import org.nothink.ballcrm.util.ComUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class NodeController {

    @Autowired
    NodeInfoService nService;

    @GetMapping("/node")
    @ResponseBody
    public Map getNodeList(){
        return nService.getNodeList();
    }
}
