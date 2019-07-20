package org.nothink.ballcrm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class StuFamilyEntity {
    private Integer sid;

    private Integer payAbility;

    private Integer payWillMother;

    private Integer payWillFather;

    private Integer classWeekday;

    @JsonFormat(pattern="HH:mm:ss",timezone="GMT+8")
    private Date classTime;

    private String demoTarget;

    private String educationIdea;

    private Integer pickup;

    private Integer deal;

    private String dealReason;

    private Date updateDate;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getPayAbility() {
        return payAbility;
    }

    public void setPayAbility(Integer payAbility) {
        this.payAbility = payAbility;
    }

    public Integer getPayWillMother() {
        return payWillMother;
    }

    public void setPayWillMother(Integer payWillMother) {
        this.payWillMother = payWillMother;
    }

    public Integer getPayWillFather() {
        return payWillFather;
    }

    public void setPayWillFather(Integer payWillFather) {
        this.payWillFather = payWillFather;
    }

    public Integer getClassWeekday() {
        return classWeekday;
    }

    public void setClassWeekday(Integer classWeekday) {
        this.classWeekday = classWeekday;
    }

    public Date getClassTime() {
        return classTime;
    }

    public void setClassTime(Date classTime) {
        this.classTime = classTime;
    }

    public String getDemoTarget() {
        return demoTarget;
    }

    public void setDemoTarget(String demoTarget) {
        this.demoTarget = demoTarget == null ? null : demoTarget.trim();
    }

    public String getEducationIdea() {
        return educationIdea;
    }

    public void setEducationIdea(String educationIdea) {
        this.educationIdea = educationIdea == null ? null : educationIdea.trim();
    }

    public Integer getPickup() {
        return pickup;
    }

    public void setPickup(Integer pickup) {
        this.pickup = pickup;
    }

    public Integer getDeal() {
        return deal;
    }

    public void setDeal(Integer deal) {
        this.deal = deal;
    }

    public String getDealReason() {
        return dealReason;
    }

    public void setDealReason(String dealReason) {
        this.dealReason = dealReason == null ? null : dealReason.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}