package org.nothink.ballcrm.service;

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
        //如果上送了截止时间，把截止时间加一天，防止选不到最后一天
        if (c.getEndDate() != null)
            c.setEndDate(DateUtils.addDate(c.getEndDate(), 0, 0, 1, 0, 0, 0, 0));

        List<Statistics> newStuNumList = sMapper.getNewStuNum(c);
        List<Statistics> demoScheduleNumList = sMapper.getDemoScheduleNum(c);
        List<Statistics> demoSignNumList = sMapper.getDemoSignNum(c);
        List<Statistics> demoCloseNumList = sMapper.getDemoCloseNum(c);
        List<Statistics> buy198NumList = sMapper.getBuy198Num(c);
        List<Statistics> myStu198NumList = sMapper.getMyStu198Num(c);
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
}
