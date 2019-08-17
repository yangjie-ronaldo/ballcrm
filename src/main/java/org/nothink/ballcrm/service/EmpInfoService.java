package org.nothink.ballcrm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.mapper.EmpInfoMapper;
import org.nothink.ballcrm.mapper.EmpRoleRelMapper;
import org.nothink.ballcrm.mapper.LoginTokenMapper;
import org.nothink.ballcrm.mapper.RolesMapper;
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
    RolesMapper rMapper;
    @Autowired
    CacheService cacheService;
    @Autowired
    EmpInfoService eService;

    // 查询本店的员工列表 不分页
    public Map<String, Object> getEmpList(EmpInfoEntity c) {
        // 先写死查第一页 1000条 查员工不分页
        Page p = PageHelper.startPage(1, 1000);
        //执行查询
        List<EmpInfoEntity> list = eMapper.getEmpList(c);
        PagedResult<EmpInfoEntity> result = new PagedResult<>(c.getCurrentPage(), 1000, (int) p.getTotal());
        if (list != null) {
            for (EmpInfoEntity e : list) {
                //翻译员工门店
                transEmpInfo(e);
                // 查询员工的角色列表
                List<EmpRoleRelEntity> roles = empRoleRelMapper.selectByEid(e.getEid());
                e.setRoles(roles);
            }
        }
        result.setItems(list);
        return ComUtils.getResp(20000, "查询成功", result);
    }

    // 查询本店的员工列表 分页
    public Map<String, Object> getEmpListPaged(EmpInfoEntity c) {
        Page p = PageHelper.startPage(c.getCurrentPage(), c.getPageSize());
        //执行查询
        List<EmpInfoEntity> list = eMapper.getEmpList(c);
        PagedResult<EmpInfoEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        if (list != null) {
            for (EmpInfoEntity e : list) {
                // 查询员工的角色列表
                List<EmpRoleRelEntity> roles = empRoleRelMapper.selectByEid(e.getEid());
                //翻译员工门店
                transEmpInfo(e);
                e.setRoles(roles);
            }
        }
        result.setItems(list);
        return ComUtils.getResp(20000, "查询成功", result);
    }

    // 更新员工信息（基本信息、角色）
    @Transactional
    public Map<String, Object> editEmp(EmpInfoEntity e){
        logger.info("待修改员工信息："+e);
        //修改员工基本信息
        eMapper.updateByPrimaryKeySelective(e);

        Integer eid = e.getEid();
        if (e.getRoles()!=null){
            //修改员工角色信息
            empRoleRelMapper.deleteByEid(eid);
            for (EmpRoleRelEntity rel:e.getRoles()){
                rel.setEid(eid);
                empRoleRelMapper.insert(rel);
            }
        }
        cacheService.freshEmpCache();
        return ComUtils.getResp(20000,"修改成功",null);
    }

    // 更新员工密码 加密后存储
    @Transactional
    public Map<String, Object> editPass(EmpPass p){
        if (p==null || p.getEid()==null){
            return ComUtils.getResp(40008,"员工编号未知",null);
        }
        EmpInfoEntity e = eMapper.selectByPrimaryKey(p.getEid());
        if (e==null){
            return ComUtils.getResp(40008,"无员工信息",null);
        }
        //原密码 loginid+pass
        String passMD5=ComUtils.encodeByMd5(e.getLoginid()+p.getOldPass());
        if (!e.getPass().equals(p.getOldPass()) && !e.getPass().equals(passMD5)){
            return ComUtils.getResp(40008,"原密码有误",null);
        }
        if (p.getNewPass()==null || !p.getNewPass().equals(p.getConfirmPass())){
            return ComUtils.getResp(40008,"两次密码不一致",null);
        }

        //存储加密后密码
        String newPassMD5=ComUtils.encodeByMd5(e.getLoginid()+p.getNewPass());
        e.setPass(newPassMD5);
        eMapper.updateByPrimaryKeySelective(e);
        return ComUtils.getResp(20000,"修改成功",e.getLoginid());
    }

    //主管重置员工密码
    @Transactional
    public Map resetPass(EmpPass p){
        if (p==null || p.getEid()==null){
            return ComUtils.getResp(40008,"员工编号有误",null);
        }
        if (p.getNewPass()==null || "".equals(p.getNewPass())){
            p.setNewPass("123456");
        }
        EmpInfoEntity e = eMapper.selectByPrimaryKey(p.getEid());
        if (e==null){
            return ComUtils.getResp(40008,"无员工信息",null);
        }
        e.setPass(p.getNewPass());
        eMapper.updateByPrimaryKeySelective(e);
        return ComUtils.getResp(20000,"重置密码成功",null);
    }

    // 查询所有角色列表
    public Map<String, Object> getAllRoles(){
        return ComUtils.getResp(20000,"查询成功",rMapper.getAllRoles());
    }

    // 新增员工 废弃
//    @Transactional
//    public Map addEmp(EmpInfoEntity e) {
//        return ComUtils.getResp(40008, "暂无用交易", null);
//    }


    //注册新员工
    @Transactional
    public Map<String, Object> register(EmpInfoEntity e) {
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
        // 刷新员工缓存
        cacheService.freshEmpCache();
        return ComUtils.getResp(20000, "注册成功", null);
    }

    //删除员工
    @Transactional
    public Map delEmp(EmpInfoEntity e){
        if (e==null || e.getEid()==null) {
            return ComUtils.getResp(40008,"删除员工编号不存在",null);
        }
        List<StuEntity> stuList = eMapper.hasStu(e);
        if (stuList!=null && stuList.size()>0){
            return ComUtils.getResp(40008,"员工名下还有资源，请转移资源后再删除",null);
        }
        eMapper.deleteByPrimaryKey(e.getEid());
        return ComUtils.getResp(20000,"删除成功",null);
    }

    //员工登录
    public Map<String, Object> loginin(EmpInfoEntity e) {
        if (StringUtils.isEmpty(e.getLoginid())) {
            return ComUtils.getResp(40008, "登录名不能为空", null);
        }

        if (StringUtils.isEmpty(e.getPass())) {
            return ComUtils.getResp(40008, "密码不能为空", null);
        }

        EmpInfoEntity rel = eMapper.getEmpByLoginid(e.getLoginid());
        if (rel == null) {
            return ComUtils.getResp(40008, "用户名或密码错误", null);
        }

        //如果表里没加密，拼接的原密码
        String loginPass=rel.getLoginid()+rel.getPass();
        String loginPassMD5=ComUtils.encodeByMd5(loginPass);
        //如果表格加了密，则直接取
        String relPass=rel.getPass();

        //如果密码直接相比不等，加密后相比也不等，则出错
        if (!relPass.equals(e.getPass()) && !loginPassMD5.equals(e.getPass()) ) {
            return ComUtils.getResp(40008, "用户名或密码错误", null);
        }

        //否则密码正确，登录成功
        Map<String, Object> map = ComUtils.getResp(20000, "登录成功", null);

        Map<String, Object> data = new HashMap<>();

        //返回eid 和 token
        data.put("eid", rel.getEid());
        eService.addLoginToken(rel.getEid(), data);
        //最后放入data
        map.put("data", data);
        return map;
    }

    // 查询单个员工信息
    public Map<String, Object> getEmpInfo(EmpInfoEntity e) {
        if (e.getEid() == null)
            return ComUtils.getResp(40008, "无员工编号", null);
        EmpInfoEntity emp = eMapper.selectByPrimaryKey(e.getEid());
        if (emp == null)
            return ComUtils.getResp(40008, "无此员工信息", null);
        // 查询员工的角色列表
        List<EmpRoleRelEntity> roles = empRoleRelMapper.selectByEid(emp.getEid());
        emp.setRoles(roles);
        return ComUtils.getResp(20000, "查询成功", emp);
    }

    //注销
    public Map<String, Object> logout(Integer eid) {
        if (eid==null)
            return ComUtils.getResp(40008,"无员工信息",null);
        LoginTokenEntity lt = ltMapper.selectByPrimaryKey(eid);
        if (lt != null) {
            lt.setStatus(0);
            ltMapper.updateByPrimaryKeySelective(lt);

        }
        return ComUtils.getResp(20000, "注销成功", null);
    }

    // 查询员工的待办统计信息
    public Map<String, Object> getTodoNums(Integer eid){
        HashMap<String, Integer> map=new HashMap<>();
        Statistics statistcs;
        //获取今日联系计划待办
        statistcs=eMapper.getTodayTodoPlan(eid);
        if (statistcs!=null && statistcs.getNum()!=0){
            map.put("todayPlanTodo",statistcs.getNum());
        } else {
            map.put("todayPlanTodo",0);
        }
        // 获取课程待办
        statistcs=eMapper.getTodoSchedule(eid);
        if (statistcs!=null && statistcs.getNum()!=0){
            map.put("courseScheduleTodo",statistcs.getNum());
        } else {
            map.put("courseScheduleTodo",0);
        }
        // 获取明日提醒待办
        statistcs=eMapper.getTomorrowTodoNotify(eid);
        if (statistcs!=null && statistcs.getNum()!=0){
            map.put("tomorrowNotifyTodo",statistcs.getNum());
        } else {
            map.put("tomorrowNotifyTodo",0);
        }
        // 获取目前无跟进学员数量（既无联系计划也无约课）
        statistcs=eMapper.getNoTraceStuNum(eid);
        if (statistcs!=null && statistcs.getNum()!=0){
            map.put("noTraceStuNum",statistcs.getNum());
        } else {
            map.put("noTraceStuNum",0);
        }
        return ComUtils.getResp(20000,"查询成功",map);
    }


    @Transactional
    // 记录并返回token
    public void addLoginToken(int eid, Map<String, Object> map) {
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
        Date now24 = new Date();
        now24 = DateUtils.addDate(now24, 0, 0, 0, 24, 0, 0, 0);
        // 24小时后超时
        lt.setExpired(now24);

        if (exsist) {
            ltMapper.updateByPrimaryKeySelective(lt);
        } else {
            ltMapper.insertSelective(lt);
        }
        map.put("token", lt.getToken());
    }

    //获取员工所属门店
    private void transEmpInfo(EmpInfoEntity e){
        e.setNodeName(cacheService.NodeCache().get(e.getNid()));
    }
}
