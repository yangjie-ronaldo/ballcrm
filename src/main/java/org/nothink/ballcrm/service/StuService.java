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
        StuEntity s=stuMapper.findById(sid);
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
    public PagedResult<StuEntity> getAllByCriteria(StuCriteria c){
        Page p= PageHelper.startPage(c.getCurrentPage(), c.getPageSize());
        //执行查询
        List<StuEntity> list = stuMapper.getAll(c);
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
    public int addOne(StuCriteria c){
        logger.info("插入前的学员信息："+c.toString());
        //1.新增学员基本信息到学员表
        c.setStatus(CodeDef.THINKING);
        c.setType(CodeDef.PROTENTIAL);
        int i=stuMapper.insertAndGetSid(c);
        int sid=c.getSid();
        logger.info("插入后的学员ID："+sid);
        //2.新增学员初始状态到学员状态流水表
        StuStatusEntity sst=new StuStatusEntity();
        sst.setSid(sid);
        sst.setStatus(CodeDef.PROTENTIAL);
        sst.setNote("潜力客户来咯！努力与他建立良好关系吧！");
        stuStatusMapper.insert(sst);
        //3.新增DEMO课程
        StuCourseEntity stc=new StuCourseEntity();
        stc.setCourseTypeId(1);
        stc.setFee(0);
        stc.setNum(1);
        stc.setSid(sid);
        stuCourseMapper.insert(stc);
        return i;
    }

    /**
     * 查询学员状态历史 按学员编号查询
     * @param c
     * @return
     */
    public PagedResult<StuStatusEntity> getStuStatusList(StuCriteria c){
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

    private void stuCodeTrans(StuEntity stu){
        //代码翻译
        stu.setSexDef(cache.CodeDefCache().get(stu.getSex()));
        stu.setTypeDef(cache.CodeDefCache().get(stu.getType()));
        stu.setStatusDef(cache.CodeDefCache().get(stu.getStatus()));
        stu.setVerifyStatusDef(cache.CodeDefCache().get(stu.getVerifyStatusDef()));
        //所属cc翻译
        stu.setCcName(cache.EmpCache().get(stu.getCc()));
    }
}
