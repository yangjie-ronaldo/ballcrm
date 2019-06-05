package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.ContactPlanEntity;
import org.nothink.ballcrm.entity.PlanCriteria;

import java.util.List;

public interface ContactPlanMapper {
    int insert(ContactPlanEntity record);
    List<ContactPlanEntity> getPlanList(PlanCriteria c);
}