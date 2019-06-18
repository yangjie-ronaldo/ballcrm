package org.nothink.ballcrm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.mapper.*;
import org.nothink.ballcrm.util.CodeDef;
import org.nothink.ballcrm.util.ComUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 根据id查询学员
     *
     * @param sid
     * @return
     */
    public StuEntity findById(int sid) {
        StuEntity s = stuMapper.selectByPrimaryKey(sid);
        //翻译代码值
        if (s != null)
            stuCodeTrans(s);
        return s;
    }

    /**
     * 根据条件筛选所有学员 分页
     *
     * @param c
     * @return
     */
    public Map getAllByCriteria(StuEntity c) {
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
     * 新增学员
     *
     * @param c
     * @return
     */
    @Transactional
    public Map addOne(StuEntity c) {
        //1.新增学员基本信息到学员表
        c.setType(CodeDef.TYPE_PROTENTIAL);
        c.setCreateDate(new Date());
        int i = stuMapper.insertSelectiveAndGetKey(c);
        int sid = c.getSid();
        logger.info("插入后的学员ID：" + sid);

        //2.更新学员状态
        updateStuStatus(c, CodeDef.STU_NEW, "新添一枚客户！即时维护加油鸭！");

        //3.赠送DEMO课程
        StuCourseEntity stc = new StuCourseEntity();
        stc.setCourseTypeId(1);
        stc.setFee(0);
        stc.setNum(1);
        stc.setSid(sid);
        stc.setCreateDate(new Date());
        stuCourseMapper.insert(stc);
        if (i > 0)
            return ComUtils.getResp(20000, "新增成功", null);
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
            return ComUtils.getResp(20000, "更新成功", null);
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
     * 学员买课
     *
     * @param sc
     * @return
     */
    @Transactional
    public Map buyCourse(StuCourseEntity sc) {
        sc.setCreateDate(new Date());
        StuEntity stu = this.findById(sc.getSid());
        if (stu == null)
            return ComUtils.getResp(40008, "无此学员", null);
        // 1.新增买课记录
        int r = stuCourseMapper.insertSelective(sc);

        // 2.不同课程，不同的处理
        String status = null, note = null;
        if (sc.getCourseTypeId() == 2) {
            //买小课包
            status = CodeDef.STU_BUY198;
            note = "成为小课包学员！再接再厉！";
            stu.setType(CodeDef.TYPE_198);
            // 把这个学员的demo课数量置为0
            StuCourseEntity demo=new StuCourseEntity();
            demo.setSid(sc.getSid());
            demo.setCourseTypeId(1);
            demo=stuCourseMapper.getStuCourseSelective(demo);
            demo.setNum(0);
            stuCourseMapper.updateByPrimaryKeySelective(demo);
        } else if (sc.getCourseTypeId() == 3) {
            //买了正课
            status = CodeDef.STU_BUYVIP;
            note = "成就达成！成为正式年卡会员!";
            stu.setType(CodeDef.TYPE_YEARVIP);
        }
        this.updateStuStatus(stu, status, note);
        if (r > 0)
            return ComUtils.getResp(20000, "操作成功", null);
        else
            return ComUtils.getResp(40008, "操作失败", null);
    }

    //能买的课程
    public Map courseBuyList(){
        return ComUtils.getResp(20000,"查询成功",courseMapper.getCourse());
    }

    /**
     * 查询学员家庭信息
     * @param c
     * @return
     */
    public Map getStuFamily(StuFamilyEntity c){
        if (c.getSid()==null){
            return ComUtils.getResp(40008,"无此学员",null);
        }
        StuFamilyEntity f=stuFamilyMapper.selectByPrimaryKey(c.getSid());
        return ComUtils.getResp(20000,"查询成功",f);
    }

    /**
     * 修改并保存家庭信息
     * @param c
     * @return
     */
    public Map saveStuFamily(StuFamilyEntity c){
        if (c.getSid()==null){
            return ComUtils.getResp(40008,"无此学员",null);
        }
        StuFamilyEntity f=stuFamilyMapper.selectByPrimaryKey(c.getSid());
        int r=0;
        if (f==null){
            r=stuFamilyMapper.insertSelective(c);
        } else {
            r=stuFamilyMapper.updateByPrimaryKeySelective(c);
        }
        if (r==0)
            return ComUtils.getResp(40008,"修改失败",null);
        else
            return ComUtils.getResp(20000,"修改成功",c);
    }

    //放弃客户处理
    @Transactional
    public int abandonStu(Integer sid) {
        StuEntity stu = stuMapper.selectByPrimaryKey(sid);
        if (stu == null)
            return 0;
        stu.setType(CodeDef.TYPE_HOUXUAN);
        this.updateStuStatus(stu, CodeDef.STU_HOUXUAN, "主管同意放弃客户，以后再维护吧");
        return 1;
    }


    //客户代码值翻译
    private void stuCodeTrans(StuEntity stu) {
        //代码翻译
        if (stu != null) {
            stu.setSexDef(cache.CodeDefCache().get(stu.getSex()));
            stu.setTypeDef(cache.CodeDefCache().get(stu.getType()));
            stu.setStatusDef(cache.CodeDefCache().get(stu.getStatus()));
            stu.setVerifyStatusDef(cache.CodeDefCache().get(stu.getVerifyStatusDef()));
            // 所属cc翻译
            stu.setCcName(cache.EmpCache().get(stu.getCc()));
            // 所属门店翻译
            stu.setNodeName(cache.NodeCache().get(stu.getNode()));
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
