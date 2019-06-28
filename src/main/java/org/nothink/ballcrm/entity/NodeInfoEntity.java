package org.nothink.ballcrm.entity;

import java.util.Date;

public class NodeInfoEntity {
    private Integer nid;
    private String name;
    private Date createDate;

    @Override
    public String toString() {
        return "NodeInfoEntity{" +
                "nid=" + nid +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                '}';
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}