package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.ExcelData;
import org.nothink.ballcrm.util.ExcelUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处理excel下载相关的演示
 */
@Controller
@RequestMapping("/ballapi")
public class ExcelController {

    @GetMapping("/exceldownload")
    public void excel(HttpServletResponse response) throws Exception {
        ExcelData data = new ExcelData();
        data.setName("用户信息数据");
        //添加表头
        List<String> titles = new ArrayList();
        //for(String title: excelInfo.getNames())
        titles.add("表头1");
        titles.add("表头2");
        titles.add("表头3");
        titles.add("表头4");
        titles.add("表头5");
        data.setTitles(titles);

        //添加行
        List<List<Object>> rows = new ArrayList();
        List<Object> row = null;
        for(int i=1; i<1000;i++){
            row=new ArrayList();
            row.add("行"+i+"列1");
            row.add("行"+i+"列2");
            row.add("行"+i+"列3");
            row.add("行"+i+"列4");
            row.add("行"+i+"列5");
            rows.add(row);
        }
        data.setRows(rows);

        //文件名输出
        SimpleDateFormat fdate=new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName=fdate.format(new Date())+".xlsx";
        ExcelUtils.exportExcel(response, fileName, data);
    }
}
