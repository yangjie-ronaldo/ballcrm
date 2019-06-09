package org.nothink.ballcrm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.nothink.ballcrm.entity.EmpInfoEntity;
import org.nothink.ballcrm.entity.EmpRoleRelEntity;
import org.nothink.ballcrm.entity.LoginTokenEntity;
import org.nothink.ballcrm.entity.PagedResult;
import org.nothink.ballcrm.mapper.EmpInfoMapper;
import org.nothink.ballcrm.mapper.EmpRoleRelMapper;
import org.nothink.ballcrm.mapper.LoginTokenMapper;
import org.nothink.ballcrm.util.ComUtils;
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
    EmpRoleRelMapper empRoleRelMapper;

    @Autowired
    EmpInfoService eService;

    // 查询本店的员工列表
    public Map getEmpList(EmpInfoEntity c) {
        if (c.getNid() == 0) {
            return ComUtils.getResp(40008,"无门店编号",null);
        }
        Page p = PageHelper.startPage(c.getCurrentPage(), c.getPageSize());
        //执行查询
        List<EmpInfoEntity> list = eMapper.getEmpList(c);
        PagedResult<EmpInfoEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        result.setItems(list);
        return ComUtils.getResp(20000,"查询成功",result);
    }

    // 新增员工 废弃
    @Transactional
    public Map addEmp(EmpInfoEntity e) {
        return ComUtils.getResp(40008,"暂无用交易",null);
    }


    //注册新员工
    @Transactional
    public Map register(EmpInfoEntity e) {
        if (StringUtils.isEmpty(e.getLoginid())) {
            return ComUtils.getResp(40008, "登录名不能为空", null);
        }
        if (StringUtils.isEmpty(e.getPass())) {
            return ComUtils.getResp(40008, "密码不能为空", null);
        }
        if (StringUtils.isEmpty(e.getName())) {
            return ComUtils.getResp(40008, "用户名不能为空", null);
        }

        EmpInfoEntity user = eMapper.getEmpByLoginid(e.getLoginid());
        if (user != null) {
            return ComUtils.getResp(40008, "登录号已被注册", null);
        }
        //TODO 先直接注册 不加密
        eMapper.insertSelectiveAndGetKey(e);
        logger.info("注册后的编号为：" + e.getEid());
        return ComUtils.getResp(20000, "注册成功", null);
    }

    //员工登录
    public Map loginin(EmpInfoEntity e) {
        if (StringUtils.isEmpty(e.getLoginid())) {
            return ComUtils.getResp(40008, "登录名不能为空", null);
        }

        if (StringUtils.isEmpty(e.getPass())) {
            return ComUtils.getResp(40008, "密码不能为空", null);
        }

        EmpInfoEntity rel = eMapper.getEmpByLoginid(e.getLoginid());
        if (rel == null) {
            return ComUtils.getResp(40008, "用户不存在", null);
        }

        if (!rel.getPass().equals(e.getPass())) {
            return ComUtils.getResp(40008, "密码错误", null);
        }

        //否则密码正确，登录成功
        Map map = ComUtils.getResp(20000, "登陆成功", null);

        Map<String, Object> data = new HashMap<>();

        //返回eid 和 token
        data.put("eid", rel.getEid());
        eService.addLoginToken(rel.getEid(), data);
        //最后放入data
        map.put("data", data);
        return map;
    }

    // 查询单个员工信息
    public Map getEmpInfo(EmpInfoEntity e) {
        if (e.getEid() == null)
            return ComUtils.getResp(40008, "无员工编号", null);
        EmpInfoEntity emp = eMapper.selectByPrimaryKey(e.getEid());
        if (emp == null)
            return ComUtils.getResp(40008, "无此员工信息", null);
        // 查询员工的角色列表
        List roles = empRoleRelMapper.selectByEid(emp.getEid());
        emp.setRoles(roles);
        return ComUtils.getResp(20000,"查询成功",emp);
    }

    //注销
    public Map logout(Integer eid) {
        LoginTokenEntity lt = ltMapper.selectByPrimaryKey(eid);
        if (lt != null) {
            lt.setStatus(0);
            ltMapper.updateByPrimaryKeySelective(lt);
            return ComUtils.getResp(20000, "注销成功", null);
        } else {
            return ComUtils.getResp(40008, "注销失败", null);
        }
    }


    @Transactional
    // 记录并返回token
    public void addLoginToken(int eid, Map map) {
        LoginTokenEntity lt = new LoginTokenEntity();
        lt.setEid(eid);
        lt = ltMapper.selectByPrimaryKey(eid);
        boolean exsist = false;
        if (lt != null) {
            exsist = true;
        }
        lt = new LoginTokenEntity();
        lt.setEid(eid);
        lt.setStatus(1);
        lt.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
        Date now15 = new Date();
        now15 = DateUtils.addDate(now15, 0, 0, 0, 0, 15, 0, 0);
        // 15分钟后超时
        lt.setExpired(now15);

        if (exsist) {
            ltMapper.updateByPrimaryKeySelective(lt);
        } else {
            ltMapper.insertSelective(lt);
        }
        map.put("token", lt.getToken());
    }
}
