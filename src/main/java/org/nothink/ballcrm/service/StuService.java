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
     * @param sid
     * @return
     */
    public StuEntity findById(int sid){
        StuEntity s=stuMapper.selectByPrimaryKey(sid);
        //翻译代码值
        if (s!=null)
            stuCodeTrans(s);
        return s;
    }

    /**
     * 根据条件筛选所有学员 分页
     * @param c
     * @return
     */
    public PagedResult<StuEntity> getAllByCriteria(StuEntity c){
        Page p= PageHelper.startPage(c.getCurrentPage(), c.getPageSize());
        //执行查询
        List<StuEntity> list = stuMapper.getStuList(c);
        PagedResult<StuEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list!=null)
            for (StuEntity stu:list)
                stuCodeTrans(stu);
        result.setItems(list);
        return result;
    }

    /**
     * 新增学员
     * @param c
     * @return
     */
    @Transactional
    public int addOne(StuEntity c){
        logger.info("插入前的学员信息："+c.toString());
        //1.新增学员基本信息到学员表
        c.setType(CodeDef.PROTENTIAL);
        c.setCreateDate(new Date());
        int i=stuMapper.insertSelectiveAndGetKey(c);
        int sid=c.getSid();
        logger.info("插入后的学员ID："+sid);

        //2.更新学员状态
        updateStuStatus(c,CodeDef.THINKING);

        //3.赠送DEMO课程
        StuCourseEntity stc=new StuCourseEntity();
        stc.setCourseTypeId(1);
        stc.setFee(0);
        stc.setNum(1);
        stc.setSid(sid);
        stc.setCreateDate(new Date());
        stuCourseMapper.insert(stc);
        return i;
    }

    /**
     * 查询学员状态历史 按学员编号查询
     * @param c
     * @return
     */
    public PagedResult<StuStatusEntity> getStuStatusList(StuEntity c){
        Page p= PageHelper.startPage(c.getCurrentPage(), c.getPageSize());

        //执行查询
        List<StuStatusEntity> list = stuStatusMapper.getStuStatusListBySid(c.getSid());
        PagedResult<StuStatusEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list!=null)
            for (StuStatusEntity s:list)
                s.setStatusDef(cache.CodeDefCache().get(s.getStatus()));
        result.setItems(list);
        return result;
    }

    /**
     * 查询学员已买课程列表
     * @param c
     */
    public List<StuCourseEntity> getStuCourseList(StuEntity c){
        return stuCourseMapper.getStuCourseListBySid(c.getSid());
    }

    private void stuCodeTrans(StuEntity stu){
        //代码翻译
        stu.setSexDef(cache.CodeDefCache().get(stu.getSex()));
        stu.setTypeDef(cache.CodeDefCache().get(stu.getType()));
        stu.setStatusDef(cache.CodeDefCache().get(stu.getStatus()));
        stu.setVerifyStatusDef(cache.CodeDefCache().get(stu.getVerifyStatusDef()));
        //所属cc翻译
        stu.setCcName(cache.EmpCache().get(stu.getCc()));
    }

    //根据客户编号更新客最新状态、记历史
    private void updateStuStatus(StuEntity stu,String status){
        //设置学员最新状态
        stu.setStatus(status);
        stuMapper.updateByPrimaryKeySelective(stu);
        //状态变化历史记录
        StuStatusEntity sta=new StuStatusEntity();
        sta.setStatus(status);
        sta.setNote("新添一枚客户！即时维护加油鸭！");
        sta.setSid(stu.getSid());
        sta.setCreateDate(new Date());
        stuStatusMapper.insertSelective(sta);
    }
}
