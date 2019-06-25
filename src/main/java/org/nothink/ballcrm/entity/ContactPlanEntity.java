package org.nothink.ballcrm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContactPlanEntity extends PagedCriteria {
    private Date startDate;
    private Date endDate;
    private Integer pkid;
    private String planNote;
    private String finishNote;
    private Date createDate;
    private Date planDate;
    private Date finishDate;
    private Integer sid;
    private String sName;
    private Integer eid;
    private String eName;
    private String status;
    private String statusDef;
    private String verifyNote;
    private Integer verifyEid;
    private String verifyEName;
    private Integer node;
}