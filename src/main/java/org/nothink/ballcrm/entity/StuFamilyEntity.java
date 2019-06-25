package org.nothink.ballcrm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class StuFamilyEntity {
    private Integer sid;
    private String payAbility;
    private String payWill;
    private Date orderDate;
    private String educationIdea;
    private String pickup;
    private String decision;
}