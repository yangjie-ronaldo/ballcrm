package org.nothink.ballcrm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class StuEntity extends PagedCriteria {
    private Integer sid;
    private Date createDate;
    private String name;
    private String nikiName;
    private Date birthday;
    private String sex;
    private String sexDef;
    private String tel;
    private String status;
    private String statusDef;
    private String verifyStatus;
    private String verifyStatusDef;
    private String channel;
    private String channelName;
    private String channelNote;
    private String type;
    private String typeDef;
    private String wechat;
    private Integer preCc;
    private Integer cc;
    private String ccName;
    private Integer teacherId;
    private String teacherIdName;
    private Integer node;
    private String nodeName;
}