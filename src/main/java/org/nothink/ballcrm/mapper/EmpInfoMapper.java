package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.EmpInfoEntity;
import org.nothink.ballcrm.entity.Statistics;
import org.nothink.ballcrm.entity.StuEntity;

import java.util.List;

public interface EmpInfoMapper {
    EmpInfoEntity getEmpByLoginid(String loginid);

    List<EmpInfoEntity> getEmpList(EmpInfoEntity e);
    List<StuEntity> hasStu(EmpInfoEntity e);

    int deleteByPrimaryKey(Integer eid);

    int insert(EmpInfoEntity record);

    int insertSelectiveAndGetKey(EmpInfoEntity record);

    EmpInfoEntity selectByPrimaryKey(Integer eid);

    int updateByPrimaryKeySelective(EmpInfoEntity record);

    int updateByPrimaryKey(EmpInfoEntity record);

    // 查询员工今日联系计划待办数量
    Statistics getTodayTodoPlan(Integer eid);

    // 查询员工待处理课程数量
    Statistics getTodoSchedule(Integer eid);

    // 查询员工明日提醒数量
    Statistics getTomorrowTodoNotify(Integer eid);

    // 获取目前无跟进学员数量（既无联系计划也无约课）
    Statistics getNoTraceStuNum(Integer eid);
}