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

    @Override
    public String toString() {
        return "StuFamilyEntity{" +
                "sid=" + sid +
                ", payAbility='" + payAbility + '\'' +
                ", payWill='" + payWill + '\'' +
                ", orderDate=" + orderDate +
                ", educationIdea='" + educationIdea + '\'' +
                ", pickup='" + pickup + '\'' +
                ", decision='" + decision + '\'' +
                '}';
    }

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
        this.payAbility = payAbility;
    }

    public String getPayWill() {
        return payWill;
    }

    public void setPayWill(String payWill) {
        this.payWill = payWill;
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
        this.educationIdea = educationIdea;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}