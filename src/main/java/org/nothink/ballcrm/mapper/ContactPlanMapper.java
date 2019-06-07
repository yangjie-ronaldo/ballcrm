package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.ContactPlanEntity;

import java.util.List;

public interface ContactPlanMapper {
    List<ContactPlanEntity> getPlanList(ContactPlanEntity c);

    List<ContactPlanEntity> getVerifyPlanByEid(ContactPlanEntity c);

    int deleteByPrimaryKey(Integer pkid);

    int insert(ContactPlanEntity record);

    int insertSelective(ContactPlanEntity record);

    ContactPlanEntity selectByPrimaryKey(Integer pkid);

    int updateByPrimaryKeySelective(ContactPlanEntity record);

    int updateByPrimaryKey(ContactPlanEntity record);
}