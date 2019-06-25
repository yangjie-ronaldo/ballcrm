package org.nothink.ballcrm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NodeInfoEntity {
    private Integer nid;
    private String name;
    private Date createDate;
}