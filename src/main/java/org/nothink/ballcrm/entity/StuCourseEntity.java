package org.nothink.ballcrm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class StuCourseEntity {
    private Integer pkid;
    private Integer sid;
    private Integer eid;
    private String eName;
    private Integer courseTypeId;
    private String courseName;
    private Integer num;
    private Integer fee;
    private Date createDate;
    private Date endDate;
    private Date updateDate;
}