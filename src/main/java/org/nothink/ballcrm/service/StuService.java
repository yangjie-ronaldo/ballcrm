package org.nothink.ballcrm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.nothink.ballcrm.entity.PagedResult;
import org.nothink.ballcrm.entity.StuCriteria;
import org.nothink.ballcrm.entity.StuEntity;
import org.nothink.ballcrm.mapper.StuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuService {
    @Autowired
    StuMapper stuMapper;
    @Autowired
    CacheService cache;

    // 根据id查询学员
    public StuEntity findById(int sid){
        StuEntity s=stuMapper.findById(sid);
        //翻译代码值
        stuCodeTrans(s);
        return s;
    }

    // 根据条件筛选所有学员 分页
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

    // 新增学员
    public int addOne(StuCriteria c){
        return stuMapper.insert(c);
    }

    private void stuCodeTrans(StuEntity stu){
        stu.setSexDef(cache.CodeDefCache().get(stu.getSex()));
        stu.setTypeDef(cache.CodeDefCache().get(stu.getType()));
        stu.setStatusDef(cache.CodeDefCache().get(stu.getStatus()));
        stu.setVerifyStatusDef(cache.CodeDefCache().get(stu.getVerifyStatusDef()));
    }
}
