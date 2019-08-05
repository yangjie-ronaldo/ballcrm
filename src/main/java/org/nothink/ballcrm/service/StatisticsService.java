package org.nothink.ballcrm.service;

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
    @Autowired
    StuService stuService;
    @Autowired
    CourseTypeMapper courseTypeMapper;

    //查看常规体验课统计
    public Map getNormalStatistics(StatisticsCriteria criteria) {
        //查询时间处理
        resetCriteria(criteria);

        //开始查询每列的数据
        //a. 新增学员数据量（按照创建时间筛选）
        List<Statistics> newStuNumList = sMapper.getNewStuNum(criteria);
        //b. 名下资源上门量（时间段内体验类课签到）
        List<Statistics> demoSignNumList = sMapper.getDemoSignNum(criteria);
        //c. 体验课约课数量（时间段内体验类课约课，只算约课数，不按学员去重）
        List<Statistics> demoScheduleNumList = sMapper.getDemoScheduleNum(criteria);
        //d. 体验课关单数量（时间段体验类课签到后，本人关单的数量）
        List<Statistics> demoCloseNumList = sMapper.getDemoCloseNum(criteria);

        //新的查询方式 二维数组输出表格
        List<List> outTable=new ArrayList<>();
        JSONObject cell=null;
        JSONObject urlparam=null;
        //构建表头
        ArrayList<JSONObject> row=new ArrayList<>();
        cell=new JSONObject();
        cell.put("value","员工编号");
        row.add(cell);
        cell=new JSONObject();
        cell.put("value","员工姓名");
        row.add(cell);
        cell=new JSONObject();
        cell.put("value","新增学员数量");
        row.add(cell);
        cell=new JSONObject();
        cell.put("value","资源上门数量");
        row.add(cell);
        cell=new JSONObject();
        cell.put("value","体验课约课数量");
        row.add(cell);
        cell=new JSONObject();
        cell.put("value","体验课关单数量");
        row.add(cell);
        outTable.add(row);

        //遍历每一行，来构建数据
        for (int i=0;i<newStuNumList.size();i++) {
            //本行
            row = new ArrayList<>();
            //获取CC的Eid
            Integer eid = newStuNumList.get(i).getEid();

            //CC的eid
            cell = new JSONObject();
            cell.put("value", eid);
            row.add(cell);
            //CC名称
            cell = new JSONObject();
            cell.put("value", cacheService.EmpCache().get(eid));
            row.add(cell);

            //新客户数量单元格
            cell = getNumCell("newStuNum", eid, criteria, newStuNumList.get(i));
            row.add(cell);
            //名下资源上门量单元格
            cell = getNumCell("demoSignNum", eid, criteria, demoSignNumList.get(i));
            row.add(cell);
            //体验课约课数量单元格
            cell = getNumCell("demoScheduleNum", eid, criteria, demoScheduleNumList.get(i));
            row.add(cell);
            //体验课关单数量单元格
            cell = getNumCell("demoCloseNum", eid, criteria, demoCloseNumList.get(i));
            row.add(cell);
            outTable.add(row);
        }
        return ComUtils.getResp(20000,"查询成功",outTable);
    }

    //查看学员按状态的统计
    public Map getStatusStatistics(StatisticsCriteria criteria){
        //获取所有学员的状态
        Map<String, List<CodeDefEntity>> codemap = cacheService.StandardCode();
        List<CodeDefEntity> statusList=codemap.get("STUSTATUS");

        //查询每种状态的数量，用一个List存下来
        List<List<Statistics>> colList=new ArrayList<>();
        List<Statistics> col=null;
        for (int i=0;i<statusList.size();i++){
            //设置查询状态
            criteria.setStatus(statusList.get(i).getCode());
            //查询数据
            col=sMapper.getStuStatusNum(criteria);
            //放入结果中
            colList.add(col);
        }

        //二维数组输出表格
        List<List> outTable=new ArrayList<>();
        JSONObject cell;
        //构建表头
        ArrayList<JSONObject> row=new ArrayList<>();
        cell=new JSONObject();
        cell.put("value","员工编号");
        row.add(cell);
        cell=new JSONObject();
        cell.put("value","员工姓名");
        row.add(cell);
        for (int i=0;i<statusList.size();i++){
            cell=new JSONObject();
            cell.put("value",statusList.get(i).getDefinition()+"数量");
            row.add(cell);
        }
        outTable.add(row);

        //下面遍历每一行，来构建数据
        for (int i=0;i<colList.get(0).size();i++) {
            //本行
            row = new ArrayList<>();
            //获取CC的Eid
            Integer eid = colList.get(0).get(i).getEid();

            //CC的eid
            cell = new JSONObject();
            cell.put("value", eid);
            row.add(cell);
            //CC名称
            cell = new JSONObject();
            cell.put("value", cacheService.EmpCache().get(eid));
            row.add(cell);

            //循环获取各列并构建单元格
            for (int j=0;j<colList.size();j++){
                //设置本列所查的状态
                criteria.setStatus(statusList.get(j).getCode());
                cell=getNumCell("statusStuNum",eid,criteria,colList.get(j).get(i));
                row.add(cell);
            }
            outTable.add(row);
        }
        return ComUtils.getResp(20000,"查询成功",outTable);
    }

    //查看购买课程关单统计
    public Map getCourseCloseNumStatistics(StatisticsCriteria criteria){
        this.resetCriteria(criteria);
        //获取营销类、正课类的课程
        List<CourseTypeEntity> courseList = courseTypeMapper.getCourseForStatistics();
        //查询每种课程相关的两种统计数据，用一个List存下来
        List<List<Statistics>> colList=new ArrayList<>();
        List<Statistics> col=null;
        //每列的关键字
        List<JSONObject> keyword=new ArrayList<>();
        //遍历课程，每种课程产生两列结果
        for (int i=0;i<courseList.size();i++){
            //设置查询的课程编号
            criteria.setCourseTypeId(courseList.get(i).getPkid());
            //之前没有购买营销课
            criteria.setHasMarket(0);
            //查询数据无营销课
            col=sMapper.getCourseCloseNum(criteria);
            //放入结果中
            colList.add(col);
            //设置当前列关键字
            JSONObject keyobb=new JSONObject();
            keyobb.put("title",courseList.get(i).getTypeName()+"关单数(体验课转)");
            keyobb.put("key","courseCloseNum");
            keyobb.put("courseTypeId",criteria.getCourseTypeId());
            keyobb.put("hasMarket",0);
            keyword.add(keyobb);

            //之前有购买营销课
            criteria.setHasMarket(1);
            //查询数据有营销课
            col=sMapper.getCourseCloseNum(criteria);
            //放入结果中
            colList.add(col);
            //设置当前列关键字
            keyobb=new JSONObject();
            keyobb.put("title",courseList.get(i).getTypeName()+"关单数(营销课转)");
            keyobb.put("key","courseCloseNum");
            keyobb.put("courseTypeId",criteria.getCourseTypeId());
            keyobb.put("hasMarket",1);
            keyword.add(keyobb);
        }

        //二维数组输出表格
        List<List> outTable=new ArrayList<>();
        JSONObject cell;
        //构建表头
        ArrayList<JSONObject> row=new ArrayList<>();
        cell=new JSONObject();
        cell.put("value","员工编号");
        row.add(cell);
        cell=new JSONObject();
        cell.put("value","员工姓名");
        row.add(cell);
        for (int i=0;i<keyword.size();i++){
            cell=new JSONObject();
            cell.put("value",keyword.get(i).getString("title"));
            row.add(cell);
        }
        outTable.add(row);

        //下面遍历每一行，来构建数据
        for (int i=0;i<colList.get(0).size();i++) {
            //本行
            row = new ArrayList<>();
            //获取CC的Eid
            Integer eid = colList.get(0).get(i).getEid();
            //CC的eid
            cell = new JSONObject();
            cell.put("value", eid);
            row.add(cell);
            //CC名称
            cell = new JSONObject();
            cell.put("value", cacheService.EmpCache().get(eid));
            row.add(cell);
            //循环获取各列并构建单元格
            for (int j=0;j<colList.size();j++){
                //设置本列所查的关键字信息
                criteria.setCourseTypeId(keyword.get(j).getInt("courseTypeId"));
                criteria.setHasMarket(keyword.get(j).getInt("hasMarket"));
                cell=getNumCell(keyword.get(j).getString("key"),eid,criteria,colList.get(j).get(i));
                row.add(cell);
            }
            outTable.add(row);
        }
        return ComUtils.getResp(20000,"查询成功",outTable);

    }

    //查看购买课程配合老师统计
    public Map getCourseCloseTeacherStatistics(StatisticsCriteria criteria){
        this.resetCriteria(criteria);
        //获取营销类、正课类的课程
        List<CourseTypeEntity> courseList = courseTypeMapper.getCourseForStatistics();
        //查询每种课程相关的两种统计数据，用一个List存下来
        List<List<Statistics>> colList=new ArrayList<>();
        List<Statistics> col=null;
        //每列的关键字
        List<JSONObject> keyword=new ArrayList<>();
        //遍历课程，每种课程产生两列结果
        for (int i=0;i<courseList.size();i++){
            //设置查询的课程编号
            criteria.setCourseTypeId(courseList.get(i).getPkid());
            //之前没有购买营销课
            criteria.setHasMarket(0);
            //查询数据无营销课
            col=sMapper.getCourseCloseTeacherNum(criteria);
            //放入结果中
            colList.add(col);
            //设置当前列关键字
            JSONObject keyobb=new JSONObject();
            keyobb.put("title",courseList.get(i).getTypeName()+"关单配合数(体验课转)");
            keyobb.put("key","courseCloseTeacherNum");
            keyobb.put("courseTypeId",criteria.getCourseTypeId());
            keyobb.put("hasMarket",0);
            keyword.add(keyobb);

            //之前有购买营销课
            criteria.setHasMarket(1);
            //查询数据有营销课
            col=sMapper.getCourseCloseNum(criteria);
            //放入结果中
            colList.add(col);
            //设置当前列关键字
            keyobb=new JSONObject();
            keyobb.put("title",courseList.get(i).getTypeName()+"关单配合数(营销课转)");
            keyobb.put("key","courseCloseTeacherNum");
            keyobb.put("courseTypeId",criteria.getCourseTypeId());
            keyobb.put("hasMarket",1);
            keyword.add(keyobb);
        }

        //二维数组输出表格
        List<List> outTable=new ArrayList<>();
        JSONObject cell;
        //构建表头
        ArrayList<JSONObject> row=new ArrayList<>();
        cell=new JSONObject();
        cell.put("value","员工编号");
        row.add(cell);
        cell=new JSONObject();
        cell.put("value","老师姓名");
        row.add(cell);
        for (int i=0;i<keyword.size();i++){
            cell=new JSONObject();
            cell.put("value",keyword.get(i).getString("title"));
            row.add(cell);
        }
        outTable.add(row);

        //下面遍历每一行，来构建数据
        for (int i=0;i<colList.get(0).size();i++) {
            //本行
            row = new ArrayList<>();
            //获取CC的Eid
            Integer eid = colList.get(0).get(i).getEid();
            //CC的eid
            cell = new JSONObject();
            cell.put("value", eid);
            row.add(cell);
            //CC名称
            cell = new JSONObject();
            cell.put("value", cacheService.EmpCache().get(eid));
            row.add(cell);
            //循环获取各列并构建单元格
            for (int j=0;j<colList.size();j++){
                //设置本列所查的关键字信息
                criteria.setCourseTypeId(keyword.get(j).getInt("courseTypeId"));
                criteria.setHasMarket(keyword.get(j).getInt("hasMarket"));
                cell=getNumCell(keyword.get(j).getString("key"),eid,criteria,colList.get(j).get(i));
                row.add(cell);
            }
            outTable.add(row);
        }
        return ComUtils.getResp(20000,"查询成功",outTable);
    }


    //查看统计信息钻取的学员
    public Map getStuDetailfromStatistics(StatisticsCriteria criteria){
        //重设时间
        resetCriteria(criteria);

        String key=criteria.getKey();
        if (key==null || "".equals(key)){
            return ComUtils.getResp(40008,"无查询类目信息",null);
        }
        //开始分情况查询
        List<StuEntity> stuList=null;
        if (key.equals("newStuNum")){
            stuList=sMapper.getNewStuNumDetails(criteria);
        } else if (key.equals("demoSignNum")){
            stuList=sMapper.getDemoSignNumDetails(criteria);
        } else if (key.equals("demoScheduleNum")){
            stuList=sMapper.getDemoScheduleNumDetails(criteria);
        } else if (key.equals("demoCloseNum")){
            stuList=sMapper.getDemoCloseNumDetails(criteria);
        } else if (key.equals("statusStuNum")){
            stuList=sMapper.getStuStatusNumDetails(criteria);
        } else if (key.equals("courseCloseNum")){
            stuList=sMapper.getCourseCloseNumDetails(criteria);
        } else if (key.equals("courseCloseTeacherNum")){
            stuList=sMapper.getCourseCloseTeacherNumDetails(criteria);
        }
        else {
            return ComUtils.getResp(40008,"查询类目有误",null);
        }
        //翻译学员列表
        if (stuList!=null && stuList.size()>0){
            for (StuEntity stu:stuList){
                stuService.stuCodeTrans(stu);
            }
        }
        return ComUtils.getResp(20000,"查询成功",stuList);
    }

    //获取按CC统计的数据  废弃
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

//            row.setBuy198Num(buy198NumList.get(i).getNum());
//            total.setBuy198Num(total.getBuy198Num() + row.getBuy198Num());

//            row.setMyStu198Num(myStu198NumList.get(i).getNum());
//            total.setMyStu198Num(total.getMyStu198Num() + row.getMyStu198Num());

            data.add(row);
        }
        data.add(total);

        return ComUtils.getResp(20000, "查询成功", data);
    }

    //重设查询的日期
    private void resetCriteria(StatisticsCriteria criteria){
        if (criteria.getStartDate() == null && criteria.getEndDate() == null) {
            //如果没有时间，默认查最近一个月
            Date todayEnd = DateUtils.getDayEndTime(DateUtils.getToday());
            criteria.setEndDate(todayEnd);
            criteria.setStartDate(DateUtils.addDate(todayEnd, 0, 0, -31, 0, 0, 0, 0));
        }
        //如果上送了截止时间，调整时间
        if (criteria.getEndDate() != null)
            criteria.setEndDate(DateUtils.getDayEndTime(criteria.getEndDate()));
    }
    //数字单元格的获取
    private JSONObject getNumCell(String numName,Integer eid,StatisticsCriteria criteria,Statistics numStatistics){
        JSONObject cell=new JSONObject();
        cell.put("value",numStatistics.getNum());
        JSONObject url=new JSONObject();
        url.put("key",numName);
        url.put("eid",eid);
        if (criteria.getStartDate()!=null){
            url.put("startDate", DateUtils.parseDateToStr(criteria.getStartDate(), DateUtils.DATE_FORMAT_YYYY_MM_DD));
        }
        if (criteria.getEndDate()!=null){
            url.put("endDate", DateUtils.parseDateToStr(criteria.getEndDate(), DateUtils.DATE_FORMAT_YYYY_MM_DD));
        }
        if (criteria.getStatus()!=null) {
            url.put("status", criteria.getStatus());
        }
        if (criteria.getCourseTypeId()!=null){
            url.put("courseTypeId",criteria.getCourseTypeId());
        }
        if (criteria.getHasMarket()!=null){
            url.put("hasMarket",criteria.getHasMarket());
        }
        cell.put("url",url);
        return cell;
    }
}
