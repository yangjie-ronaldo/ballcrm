package org.nothink.ballcrm.entity;

import lombok.Data;

import java.util.Date;
@Data
public class StuStatusEntity {
    private Integer pkid;
    private Integer sid;
    private Date createDate;
    private String status;
    private String statusDef;
    private String note;
}