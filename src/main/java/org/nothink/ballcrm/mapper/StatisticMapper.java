package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.Statistics;
import org.nothink.ballcrm.entity.StatisticsCriteria;
import java.util.List;

public interface StatisticMapper {
    //查询按员工统计的新学员数量
    List<Statistics> getNewStuNum(StatisticsCriteria c);
    //查询按员工统计的DEMO约课数
    List<Statistics> getDemoScheduleNum(StatisticsCriteria c);
    //查询按员工统计的DEMO签到数
    List<Statistics> getDemoSignNum(StatisticsCriteria c);
    //查询按员工统计的DEMO关单数（这个demo家长由谁去勾兑的）
    List<Statistics> getDemoCloseNum(StatisticsCriteria c);
    //查询按员工统计的198成交数
    List<Statistics> getBuy198Num(StatisticsCriteria c);
    //查询名下购198学员数
    List<Statistics> getMyStu198Num(StatisticsCriteria c);
}
