package org.nothink.ballcrm.entity;

import java.util.Date;

public class StuFamilyEntity {
    private Integer sid;

    private String payAbility;

    private String payWill;

    private Date orderDate;

    private String educationIdea;

    private String pickup;

    private String decision;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getPayAbility() {
        return payAbility;
    }

    public void setPayAbility(String payAbility) {
        this.payAbility = payAbility == null ? null : payAbility.trim();
    }

    public String getPayWill() {
        return payWill;
    }

    public void setPayWill(String payWill) {
        this.payWill = payWill == null ? null : payWill.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getEducationIdea() {
        return educationIdea;
    }

    public void setEducationIdea(String educationIdea) {
        this.educationIdea = educationIdea == null ? null : educationIdea.trim();
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup == null ? null : pickup.trim();
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision == null ? null : decision.trim();
    }
}