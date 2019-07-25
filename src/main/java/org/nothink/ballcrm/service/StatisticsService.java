package org.nothink.ballcrm.service;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.mapper.*;
import org.nothink.ballcrm.util.ComUtils;
import org.nothink.ballcrm.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticsService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    StatisticMapper sMapper;
    @Autowired
    CacheService cacheService;

    //获取按CC统计的数据
    public Map getCcStatistics(StatisticsCriteria c) {
        if (c.getStartDate() == null && c.getEndDate() == null) {
            //如果没有时间，默认查最近一个月
            Date todayEnd = DateUtils.getDayEndTime(DateUtils.getToday());
            c.setEndDate(todayEnd);
            c.setStartDate(DateUtils.addDate(todayEnd, 0, 0, -31, 0, 0, 0, 0));
        }
        //如果上送了截止时间，调整时间
        if (c.getEndDate() != null)
            c.setEndDate(DateUtils.getDayEndTime(c.getEndDate()));

        //查询到的每一列数据
        List<Statistics> newStuNumList = sMapper.getNewStuNum(c);
        List<Statistics> demoScheduleNumList = sMapper.getDemoScheduleNum(c);
        List<Statistics> demoSignNumList = sMapper.getDemoSignNum(c);
        List<Statistics> demoCloseNumList = sMapper.getDemoCloseNum(c);
        List<Statistics> buy198NumList = sMapper.getBuy198Num(c);
        List<Statistics> myStu198NumList = sMapper.getMyStu198Num(c);
        //新的查询方式 有连接
//        List<JSONObject> outlist=new ArrayList<>();
//        JSONObject con=null;
//        //设置urlparam
//        JSONObject urlparam=null;
//        //构建表头
//        JSONObject title=new JSONObject();
//        con=new JSONObject();
//        con.put("value","员工编号");
//        title.put("cc",con);
//        con=new JSONObject();
//        con.put("value","员工姓名");
//        title.put("ccName",con);
//        con=new JSONObject();
//        con.put("value","新客户数量");
//        title.put("newStuNum",con);
//        con=new JSONObject();
//        con.put("value","Demo约课数量");
//        title.put("demoScheduleNum",con);
//        con=new JSONObject();
//        con.put("value","Demo签到数量");
//        title.put("demoSignNum",con);
//        con=new JSONObject();
//        con.put("value","Demo关单数量");
//        title.put("demoCloseNum",con);
//        outlist.add(title);
//
//        //遍历每一行，来构建数据
//        for (int i=0;i<newStuNumList.size();i++){
//            //本行
//            JSONObject row=new JSONObject();
//
//            //获取CC的Eid
//            Integer eid=newStuNumList.get(i).getEid();
//
//            //CC的eid
//            con=new JSONObject();
//            con.put("value",eid);
//            con.put("urlparam",null);
//            row.put("cc",con);
//
//            //CC名
//            con=new JSONObject();
//            con.put("value",cacheService.EmpCache().get(eid));
//            con.put("urlparam",null);
//            row.put("ccName",con);
//
//            //新客户数量
//            con=new JSONObject();
//            con.put("value",newStuNumList.get(i).getNum());
//            urlparam=this.getUrlparam(c);
//            urlparam.put("key","newStuNum");
//            urlparam.put("cc",eid);
//            con.put("urlparam",urlparam);
//            row.put("newStuNum",con);
//
//            //Demo约课数
//            con=new JSONObject();
//            con.put("value",demoScheduleNumList.get(i).getNum());
//            urlparam=this.getUrlparam(c);
//            urlparam.put("key","demoScheduleNum");
//            urlparam.put("cc",eid);
//            con.put("urlparam",urlparam);
//            row.put("demoScheduleNum",con);
//
//            //Demo签到数
//            con=new JSONObject();
//            con.put("value",demoSignNumList.get(i).getNum());
//            urlparam=this.getUrlparam(c);
//            urlparam.put("key","demoSignNum");
//            urlparam.put("cc",eid);
//            con.put("urlparam",urlparam);
//            row.put("demoSignNum",con);
//
//            //Demo关单数
//            con=new JSONObject();
//            con.put("value",demoCloseNumList.get(i).getNum());
//            urlparam=this.getUrlparam(c);
//            urlparam.put("key","demoCloseNum");
//            urlparam.put("cc",eid);
//            con.put("urlparam",urlparam);
//            row.put("demoCloseNum",con);
//
//            outlist.add(row);
//        }
//
//        return ComUtils.getResp(20000,"查询成功",outlist);

        //老的查询方式
        //最终返回的每一行数据
        List<CcStatistics> data = new ArrayList<>();
        CcStatistics total = new CcStatistics(0, "总计", 0, 0, 0, 0, 0, 0);
        for (int i = 0; i < newStuNumList.size(); i++) {
            CcStatistics row = new CcStatistics();
            row.setCc(newStuNumList.get(i).getEid());
            row.setCcName(cacheService.EmpCache().get(row.getCc()));

            row.setNewStuNum(newStuNumList.get(i).getNum());
            total.setNewStuNum(total.getNewStuNum() + row.getNewStuNum());

            row.setDemoScheduleNum(demoScheduleNumList.get(i).getNum());
            total.setDemoScheduleNum(total.getDemoScheduleNum() + row.getDemoScheduleNum());

            row.setDemoSignNum(demoSignNumList.get(i).getNum());
            total.setDemoSignNum(total.getDemoSignNum() + row.getDemoSignNum());

            row.setDemoCloseNum(demoCloseNumList.get(i).getNum());
            total.setDemoCloseNum(total.getDemoCloseNum() + row.getDemoCloseNum());

            row.setBuy198Num(buy198NumList.get(i).getNum());
            total.setBuy198Num(total.getBuy198Num() + row.getBuy198Num());

            row.setMyStu198Num(myStu198NumList.get(i).getNum());
            total.setMyStu198Num(total.getMyStu198Num() + row.getMyStu198Num());

            data.add(row);
        }
        data.add(total);

        return ComUtils.getResp(20000, "查询成功", data);
    }

    private JSONObject getUrlparam(StatisticsCriteria c){
        JSONObject urlparam=new JSONObject();
        urlparam.put("startDate",DateUtils.parseDateToStr(c.getStartDate(),DateUtils.DATE_FORMAT_YY_MM_DD));
        urlparam.put("endDate",DateUtils.parseDateToStr(c.getEndDate(),DateUtils.DATE_FORMAT_YY_MM_DD));
        urlparam.put("nid",c.getNid());
        return urlparam;

    }
}
