package org.nothink.ballcrm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.mapper.*;
import org.nothink.ballcrm.util.CodeDef;
import org.nothink.ballcrm.util.ComUtils;
import org.nothink.ballcrm.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StuService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    StuMapper stuMapper;
    @Autowired
    StuStatusMapper stuStatusMapper;
    @Autowired
    StuCourseMapper stuCourseMapper;
    @Autowired
    CacheService cache;
    @Autowired
    CourseTypeMapper courseMapper;
    @Autowired
    StuFamilyMapper stuFamilyMapper;
    @Autowired
    EmpInfoMapper empMapper;
    @Autowired
    CourseBuyRecordMapper courseBuyRecordMapper;
    @Autowired
    CourseTypeMapper courseTypeMapper;

    /**
     * 根据id查询学员
     * @param sid
     * @return
     */
    public StuEntity findById(Integer sid) {
        StuEntity s = stuMapper.selectByPrimaryKey(sid);
        //翻译代码值
        if (s != null)
            stuCodeTrans(s);
        return s;
    }

    /**
     * 根据条件筛选所有学员 分页
     * @param c
     * @return
     */
    public Map getAllByCriteria(StuEntity c) {
        //如果上送了截止时间，把截止时间加一天，防止选不到最后一天
        if (c.getEndDate()!=null)
            c.setEndDate(DateUtils.addDate(c.getEndDate(),0,0,1,0,0,0,0));

        Page p = PageHelper.startPage(c.getCurrentPage(), c.getPageSize());
        //执行查询
        List<StuEntity> list = stuMapper.getStuList(c);
        PagedResult<StuEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list != null)
            for (StuEntity stu : list)
                stuCodeTrans(stu);
        result.setItems(list);
        return ComUtils.getResp(20000, "查询成功", result);
    }

    /**
     * 查询无追踪学员列表
     * @param c
     * @return
     */
    public Map getNoTraceStu(StuEntity c) {
        Page p = PageHelper.startPage(c.getCurrentPage(), c.getPageSize());
        //执行查询
        List<StuEntity> list = stuMapper.getNoTraceStuList(c);
        PagedResult<StuEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list != null)
            for (StuEntity s : list)
                stuCodeTrans(s);
        result.setItems(list);
        return ComUtils.getResp(20000, "查询成功", result);
    }

    /**
     * 新增学员
     * @param c
     * @return
     */
    @Transactional
    public Map addOne(StuEntity c) {
        Date now=new Date();
        //1.新增学员基本信息到学员表
        c.setType(CodeDef.TYPE_PROTENTIAL);
        c.setCreateDate(now);
        c.setUpdateDate(now);
        int i = stuMapper.insertSelectiveAndGetKey(c);
        int sid = c.getSid();
        logger.info("插入后的学员ID：" + sid);

        //2.更新学员状态
        updateStuStatus(c, CodeDef.STU_NEW, "新添一枚体验客户！即时维护哟！");

        //3.赠送体验课
        //查询当前体验课
        CourseTypeEntity criteria=new CourseTypeEntity();
        criteria.setPhaseId(2); //课程阶段为2 体验课
        List<CourseTypeEntity> courseList = courseMapper.getCourseByCriteria(criteria);
        if (courseList!=null && courseList.size()>=1){
            //选第一节体验课
            CourseTypeEntity course=courseList.get(0);
            //给学员添加体验课
            StuCourseEntity stc = new StuCourseEntity();
            stc.setCourseTypeId(course.getPkid());
            stc.setFee(course.getFee());
            stc.setNum(course.getNum());
            stc.setSid(sid);

            stc.setCreateDate(now);
            stc.setEndDate(DateUtils.addDate(now,0,0,course.getValidDay(),0,0,0,0));
            stc.setUpdateDate(now);
            stuCourseMapper.insert(stc);
        } else {
            return ComUtils.getResp(40008,"无体验课，无法新增学员",null);
        }

        if (i > 0)
            return ComUtils.getResp(20000, "新增成功", c);
        else
            return ComUtils.getResp(40008, "新增失败", null);
    }

    /**
     * 更新学员基本信息 id
     */
    @Transactional
    public Map updateStuBySid(StuEntity c) {
        logger.info("更新学员基本信息" + c.getSid());
        int i = stuMapper.updateByPrimaryKeySelective(c);
        if (i > 0)
            return ComUtils.getResp(20000, "更新成功", c);
        else
            return ComUtils.getResp(40008, "更新失败", null);
    }

    /**
     * 查询学员状态历史 按学员编号查询
     *
     * @param c
     * @return
     */
    public Map getStuStatusList(StuEntity c) {
        Page p = PageHelper.startPage(c.getCurrentPage(), c.getPageSize());

        //执行查询
        List<StuStatusEntity> list = stuStatusMapper.getStuStatusListBySid(c.getSid());
        PagedResult<StuStatusEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list != null)
            for (StuStatusEntity s : list)
                s.setStatusDef(cache.CodeDefCache().get(s.getStatus()));
        result.setItems(list);
        return ComUtils.getResp(20000, "查询成功", result);
    }

    /**
     * 查询学员已买课程列表
     *
     * @param c
     */
    public Map getStuCourseList(StuEntity c) {
        return ComUtils.getResp(20000, "查询成功", stuCourseMapper.getStuCourseListBySid(c.getSid()));
    }
    /**
     * 查询学员能约课程列表
     *
     * @param c
     */
    public Map getStuCourseToBook(StuEntity c) {
        return ComUtils.getResp(20000, "查询成功", stuCourseMapper.getStuCourseListToBook(c.getSid()));
    }

    /**
     * 学员买课
     * @param sc
     * @return
     */
    @Transactional
    public Map buyCourse(StuCourseEntity sc) {
        StuEntity stu = this.findById(sc.getSid());
        if (stu == null)
            return ComUtils.getResp(40008, "学员无效", null);
        //查询购买的课程的信息
        CourseTypeEntity course = courseMapper.selectByPrimaryKey(sc.getCourseTypeId());
        if (course==null)
            return ComUtils.getResp(40008,"无购买的课程信息",null);
        if (sc.getCreateDate()==null)
            return ComUtils.getResp(40008,"无买课程生效日期",null);
        if (sc.getEid()==null)
            return ComUtils.getResp(40008,"无关单人",null);

        Date now=new Date();
        //查看是否有买过营销类课程
        CourseBuyRecordEntity buyCriteria=new CourseBuyRecordEntity();
        buyCriteria.setSid(sc.getSid());
        List marketCourseList=courseBuyRecordMapper.getMarketCourseBuyedList(buyCriteria);

        //1.记录买课流水
        CourseBuyRecordEntity record=new CourseBuyRecordEntity();
        record.setSid(sc.getSid());
        record.setEid(sc.getEid());
        record.setCourseTypeId(sc.getCourseTypeId());
        record.setFee(sc.getFee());
        //新增买课备注
        record.setNote(sc.getNote());
        //新增买课配合老师
        record.setTeacherId(sc.getTeacherId());
        if (marketCourseList!=null && marketCourseList.size()>=1){
            record.setHasMarket(1);  //记录这次买课前有买过营销课
        } else {
            record.setHasMarket(0);  //记录这次买课前未买过营销课
        }
        record.setCreateDate(now);
        courseBuyRecordMapper.insertSelective(record);

        //2.是否已买相应课程处理
        int r;
        StuCourseEntity criteria=new StuCourseEntity();
        criteria.setSid(sc.getSid());
        criteria.setCourseTypeId(sc.getCourseTypeId());
        StuCourseEntity relSc = stuCourseMapper.getStuCourseSelective(criteria);
        if (relSc==null){
            //未买过
            //开始日期由前端传入sc.setCreateDate()
            //更新日期
            sc.setUpdateDate(now);
            //截止日期为开始加上课程的生效天数
            sc.setEndDate(DateUtils.addDate(sc.getCreateDate(),0,0,course.getValidDay(),0,0,0,0));
            sc.setNum(course.getNum());
            //新增课时记录
            r = stuCourseMapper.insertSelective(sc);
        } else {
            //买过
            if (relSc.getNum()==0){
                //已经上完了 更新
                relSc.setCreateDate(sc.getCreateDate());
                relSc.setUpdateDate(sc.getCreateDate());
                relSc.setEndDate(DateUtils.addDate(sc.getCreateDate(),0,0,course.getValidDay(),0,0,0,0));
                relSc.setNum(course.getNum());
                relSc.setEid(sc.getEid());
                //总费用累加
                relSc.setFee(relSc.getFee()+sc.getFee());
            } else {
                //还没上完 直接改结束日期 当前日期加生效天数
                relSc.setUpdateDate(now);
                relSc.setEndDate(DateUtils.addDate(now,0,0,course.getValidDay(),0,0,0,0));
                relSc.setNum(relSc.getNum()+course.getNum());
                relSc.setEid(sc.getEid());
                //总费用累加
                relSc.setFee(relSc.getFee()+sc.getFee());

            }
            r = stuCourseMapper.updateByPrimaryKeySelective(relSc);
        }

        // 2.不同课程阶段，不同的状态变化
        String status = null, note = null;
        if (course.getPhaseId() == 3) {
            //买营销课
            status = CodeDef.STU_SALE_READY;  //营销课待开班
            note = "成为营销课学员！";
            stu.setType(CodeDef.TYPE_SALE);
        } else if (course.getPhaseId() == 4) {
            //买了正课
            status = CodeDef.STU_VIP_READY;
            note = "成为正课学员!";
            stu.setType(CodeDef.TYPE_YEARVIP);
        }
        //设置学员主表买课的最新时间
        stu.setUpdateDate(now);
        this.updateStuStatus(stu, status, note);
        if (r > 0)
            return ComUtils.getResp(20000, "操作成功", null);
        else
            return ComUtils.getResp(40008, "操作失败", null);
    }

    //能买的课程
    public Map courseBuyList() {
        return ComUtils.getResp(20000, "查询成功", courseMapper.getCourseForBuy());
    }

    //所有课程
    public Map courseAll(){
        List<CourseTypeEntity> course= courseTypeMapper.getCourse();
        return ComUtils.getResp(20000,"查询成功",course);
    }

    /**
     * 查询学员家庭信息
     *
     * @param c
     * @return
     */
    public Map getStuFamily(StuFamilyEntity c) {
        if (c.getSid() == null) {
            return ComUtils.getResp(40008, "无此学员", null);
        }
        StuFamilyEntity f = stuFamilyMapper.selectByPrimaryKey(c.getSid());
        return ComUtils.getResp(20000, "查询成功", f);
    }

    /**
     * 修改并保存家庭信息
     *
     * @param c
     * @return
     */
    public Map saveStuFamily(StuFamilyEntity c) {
        if (c.getSid() == null) {
            return ComUtils.getResp(40008, "无此学员", null);
        }
        c.setUpdateDate(new Date());
        StuFamilyEntity f = stuFamilyMapper.selectByPrimaryKey(c.getSid());
        int r = 0;
        if (f == null) {
            r = stuFamilyMapper.insertSelective(c);
        } else {
            r = stuFamilyMapper.updateByPrimaryKeySelective(c);
        }
        if (r == 0)
            return ComUtils.getResp(40008, "修改失败", null);
        else
            return ComUtils.getResp(20000, "修改成功", c);
    }

    /**
     * 查询待转移学员列表
     *
     * @param c
     * @return
     */
    public Map getTransStuList(StuTransCriteria c) {
        if (c == null || StringUtils.isEmpty(c.getFromCc())) {
            return ComUtils.getResp(40008, "未选择所属员工", null);
        }
        Page p = PageHelper.startPage(c.getCurrentPage(), c.getPageSize());
        //执行查询
        List<StuEntity> list = stuMapper.getStuTransList(c);
        PagedResult<StuEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list != null)
            for (StuEntity s : list)
                s.setStatusDef(cache.CodeDefCache().get(s.getStatus()));
        result.setItems(list);
        return ComUtils.getResp(20000, "查询成功", result);

    }

    /**
     * 执行转移
     *
     * @param c
     * @return
     */
    @Transactional
    public Map doTrans(StuTransCriteria c) {
        if (c == null || StringUtils.isEmpty(c.getFromCc())) {
            return ComUtils.getResp(40008, "未选择所属员工", null);
        }
        if (StringUtils.isEmpty(c.getToCc())) {
            return ComUtils.getResp(40008, "未选择转移后员工", null);
        }

        if (c.getStus() != null && c.getStus().length > 0) {
            // 若选择了学员 则直接转移所选学员
            for (Integer sid : c.getStus()) {
                transStu(c.getFromCc(), c.getToCc(), sid);
            }
            return ComUtils.getResp(20000, "转移成功", null);
        } else {
            // 若未选择学员 按条件查询待转移
            List<StuEntity> list = stuMapper.getStuTransList(c);
            for (StuEntity stu : list) {
                transStu(c.getFromCc(), c.getToCc(), stu.getSid());
            }
            return ComUtils.getResp(20000, "转移成功", null);
        }
    }

    /**
     * 主管查询198学员情况列表
     * @param c
     * @return
     */
    public Map getStu198List(StuEntity c){
        Page p = PageHelper.startPage(c.getCurrentPage(), c.getPageSize());
        //执行查询
        List<StuEntity> list = stuMapper.getStu198List(c);
        PagedResult<StuEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list != null)
            for (StuEntity s : list)
                stuCodeTrans(s);
        result.setItems(list);
        return ComUtils.getResp(20000, "查询成功", result);

    }

    //放弃客户处理
    @Transactional
    public int abandonStu(Integer sid) {
        StuEntity stu = stuMapper.selectByPrimaryKey(sid);
        if (stu == null)
            throw new CommonException(40008,"无此学员");
        //根据学员的类型来置成不同的放弃状态
        String nowStatus=stu.getType();
        String afterStatus = null;
        if (CodeDef.TYPE_PROTENTIAL.equals(nowStatus)){
            afterStatus=CodeDef.STU_BOOKED_ABANDON;
        } else if (CodeDef.TYPE_SALE.equals(nowStatus)){
            afterStatus=CodeDef.STU_SALE_ABANDON;
        } else if (CodeDef.TYPE_YEARVIP.equals(nowStatus)){
            //TODO 正课学员放弃改状态
            afterStatus="";
        }
        //修改类型
        stu.setType(CodeDef.TYPE_HOUXUAN);
        this.updateStuStatus(stu, afterStatus, "暂放弃客户，以后再维护吧");
        return 1;
    }

    // 单个学员转移
    private boolean transStu(Integer from, Integer to, Integer sid) {
        StuEntity stu = stuMapper.selectByPrimaryKey(sid);
        EmpInfoEntity e=empMapper.selectByPrimaryKey(to);
        if (stu == null || e==null) return false;
        Integer preCc = stu.getCc();
        if (preCc != from) return false;
        stu.setPreCc(preCc);
        stu.setCc(to);
        //设置到转出员工的机构
        stu.setNode(e.getNid());
        stuMapper.updateByPrimaryKeySelective(stu);
        return true;
    }

    //客户代码值翻译 通用处理
    public void stuCodeTrans(StuEntity stu) {
        //代码翻译
        if (stu != null) {
            stu.setSexDef(cache.CodeDefCache().get(stu.getSex()));
            stu.setTypeDef(cache.CodeDefCache().get(stu.getType()));
            stu.setStatusDef(cache.CodeDefCache().get(stu.getStatus()));
            stu.setVerifyStatusDef(cache.CodeDefCache().get(stu.getVerifyStatusDef()));
            stu.setChannelName(cache.CodeDefCache().get(stu.getChannel()));

            // 所属cc翻译
            stu.setCcName(cache.EmpCache().get(stu.getCc()));
            // 所属门店翻译
            stu.setNodeName(cache.NodeCache().get(stu.getNode()));
            // 新增关联的老师翻译
            stu.setTeacherIdName(cache.EmpCache().get(stu.getTeacherId()));
            // 新增关联推广员翻译
            stu.setPopularizeIdName(cache.EmpCache().get(stu.getPopularizeId()));

            // 年龄翻译
            if (stu.getBirthday()!=null){
                Integer age=Math.toIntExact(DateUtils.getDistanceYears(stu.getBirthday(), new Date()));
                stu.setAge(age);
            }

        }
    }

    //根据客户 更新客最新状态、记历史
    public void updateStuStatus(StuEntity stu, String status, String note) {
        //设置学员最新状态
        stu.setStatus(status);
        stuMapper.updateByPrimaryKeySelective(stu);
        //状态变化历史记录
        StuStatusEntity sta = new StuStatusEntity();
        sta.setStatus(status);
        sta.setNote(note);
        sta.setSid(stu.getSid());
        sta.setCreateDate(new Date());
        stuStatusMapper.insertSelective(sta);
    }
}
