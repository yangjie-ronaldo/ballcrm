package org.nothink.ballcrm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.mapper.StuCourseMapper;
import org.nothink.ballcrm.mapper.StuMapper;
import org.nothink.ballcrm.mapper.StuStatusMapper;
import org.nothink.ballcrm.util.CodeDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

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
    public PagedResult<StuEntity> getAllByCriteria(StuEntity c) {
        Page p = PageHelper.startPage(c.getCurrentPage(), c.getPageSize());
        //执行查询
        List<StuEntity> list = stuMapper.getStuList(c);
        PagedResult<StuEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list != null)
            for (StuEntity stu : list)
                stuCodeTrans(stu);
        result.setItems(list);
        return result;
    }

    /**
     * 新增学员
     *
     * @param c
     * @return
     */
    @Transactional
    public int addOne(StuEntity c) {
        logger.info("插入前的学员信息：" + c.toString());
        //1.新增学员基本信息到学员表
        c.setType(CodeDef.STU_NEW);
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
        return i;
    }

    /**
     * 更新学员基本信息 id
     */
    @Transactional
    public int updateStuBySid(StuEntity c) {
        logger.info("更新学员基本信息" + c.getSid());
        return stuMapper.updateByPrimaryKeySelective(c);
    }

    /**
     * 查询学员状态历史 按学员编号查询
     *
     * @param c
     * @return
     */
    public PagedResult<StuStatusEntity> getStuStatusList(StuEntity c) {
        Page p = PageHelper.startPage(c.getCurrentPage(), c.getPageSize());

        //执行查询
        List<StuStatusEntity> list = stuStatusMapper.getStuStatusListBySid(c.getSid());
        PagedResult<StuStatusEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list != null)
            for (StuStatusEntity s : list)
                s.setStatusDef(cache.CodeDefCache().get(s.getStatus()));
        result.setItems(list);
        return result;
    }

    /**
     * 查询学员已买课程列表
     *
     * @param c
     */
    public List<StuCourseEntity> getStuCourseList(StuEntity c) {
        return stuCourseMapper.getStuCourseListBySid(c.getSid());
    }

    /**
     * 学员买课
     *
     * @param sc
     * @return
     */
    @Transactional
    public int buyCourse(StuCourseEntity sc) {
        sc.setCreateDate(new Date());
        StuEntity stu = this.findById(sc.getSid());
        if (stu == null)
            return 0;
        // 1.新增买课记录
        int r=stuCourseMapper.insertSelective(sc);

        // 2.修改学员状态
        String status = null, note = null;
        if (sc.getCourseTypeId() == 2) {
            //买小课包
            status = CodeDef.STU_BUY198;
            note = "成为小课包学员！再接再厉！";
            stu.setType(CodeDef.TYPE_198);
        } else if (sc.getCourseTypeId() == 3) {
            //买了正课
            status = CodeDef.STU_BUYVIP;
            note = "成就达成！成为正式年卡会员!";
            stu.setType(CodeDef.TYPE_YEARVIP);
        }
        this.updateStuStatus(stu, status, note);
        return r;
    }

    //放弃客户处理
    public int abandonStu(Integer sid){
        StuEntity stu=stuMapper.selectByPrimaryKey(sid);
        if (stu==null)
            return 0;
        stu.setType(CodeDef.TYPE_HOUXUAN);
        this.updateStuStatus(stu,CodeDef.STU_HOUXUAN,"主管同意放弃客户，以后再维护吧");
        return 1;
    }

    private void stuCodeTrans(StuEntity stu) {
        //代码翻译
        if (stu!=null){
            stu.setSexDef(cache.CodeDefCache().get(stu.getSex()));
            stu.setTypeDef(cache.CodeDefCache().get(stu.getType()));
            stu.setStatusDef(cache.CodeDefCache().get(stu.getStatus()));
            stu.setVerifyStatusDef(cache.CodeDefCache().get(stu.getVerifyStatusDef()));
            //所属cc翻译
            stu.setCcName(cache.EmpCache().get(stu.getCc()));
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
