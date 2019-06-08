package org.nothink.ballcrm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.nothink.ballcrm.entity.EmpInfoEntity;
import org.nothink.ballcrm.entity.LoginTokenEntity;
import org.nothink.ballcrm.entity.PagedResult;
import org.nothink.ballcrm.mapper.EmpInfoMapper;
import org.nothink.ballcrm.mapper.LoginTokenMapper;
import org.nothink.ballcrm.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.*;

@Service
public class EmpInfoService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EmpInfoMapper eMapper;
    @Autowired
    LoginTokenMapper ltMapper;
    @Autowired
    EmpInfoService eService;

    // 查询本店的员工列表
    public PagedResult<EmpInfoEntity> getEmpList(EmpInfoEntity c){
        if (c.getNid()==0){
            return null;
        }
        Page p = PageHelper.startPage(c.getCurrentPage(), c.getPageSize());
        //执行查询
        List<EmpInfoEntity> list = eMapper.getEmpList(c);
        PagedResult<EmpInfoEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        result.setItems(list);
        return result;
    }

    // 新增员工 废弃
    @Transactional
    public int addEmp(EmpInfoEntity e){
        return 1;
    }


    //注册新员工
    @Transactional
    public Map<String,Object> register(EmpInfoEntity e){
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isEmpty(e.getLoginid())){
            map.put("msg", "登录名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(e.getPass())){
            map.put("msg","密码不能为空");
            return  map;
        }
        if(StringUtils.isEmpty(e.getName())){
            map.put("msg","用户名不能为空");
            return  map;
        }

        EmpInfoEntity user = eMapper.getEmpByLoginid(e.getLoginid());
        if(user != null){
            map.put("msg","登录号已被注册");
            return  map;
        }
        //TODO 先直接注册 不加密
        eMapper.insertSelectiveAndGetKey(e);

        System.out.println("注册后的编号为："+e.getEid());

        return map;
    }

    //登录
    public Map loginin(EmpInfoEntity e){
        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isEmpty(e.getLoginid())){
            map.put("msg","登录名不能为空");
            return map;
        }

        if(StringUtils.isEmpty(e.getPass())){
            map.put("msg","密码不能为空");
            return map;
        }

        EmpInfoEntity rel = eMapper.getEmpByLoginid(e.getLoginid());
        if (rel == null){
            map.put("msg","登录名不存在");
            return map;
        }

        if (!rel.getPass().equals(e.getPass())) {
            map.put("msg", "密码错误");
            return map;
        }

        //否则密码正确，记录token
        eService.addLoginToken(rel.getEid(),map);
        //放入员工信息
        map.put("data",rel);
        return map;
    }

    //注销
    public Map logout(Integer eid){
        Map<String,Object> map = new HashMap<>();
        LoginTokenEntity lt = ltMapper.selectByPrimaryKey(eid);
        if (lt!=null){
            lt.setStatus(0);
            ltMapper.updateByPrimaryKeySelective(lt);
        }
        map.put("msg","注销成功");
        map.put("status",20000);
        return map;
    }


    @Transactional
    // 记录并返回token
    public void addLoginToken(int eid,Map map){
        LoginTokenEntity lt=new LoginTokenEntity();
        lt.setEid(eid);
        lt=ltMapper.selectByPrimaryKey(eid);
        boolean exsist=false;
        if (lt!=null) {
            exsist = true;
        }
        lt=new LoginTokenEntity();
        lt.setEid(eid);
        lt.setStatus(1);
        lt.setToken(UUID.randomUUID().toString().replaceAll("-",""));
        Date now15=new Date();
        now15=DateUtils.addDate(now15,0,0,0,0,15,0,0);
        lt.setExpired(now15);

        if (exsist){
            ltMapper.updateByPrimaryKeySelective(lt);
        } else {
            ltMapper.insertSelective(lt);
        }
        map.put("token",lt.getToken());
    }
}
