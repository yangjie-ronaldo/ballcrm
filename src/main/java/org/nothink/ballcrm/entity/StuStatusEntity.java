package org.nothink.ballcrm.entity;

import java.util.Date;
public class StuStatusEntity {
    private Integer pkid;
    private Integer sid;
    private Date createDate;
    private String status;
    private String statusDef;
    private String note;

    @Override
    public String toString() {
        return "StuStatusEntity{" +
                "pkid=" + pkid +
                ", sid=" + sid +
                ", createDate=" + createDate +
                ", status='" + status + '\'' +
                ", statusDef='" + statusDef + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public Integer getPkid() {
        return pkid;
    }

    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDef() {
        return statusDef;
    }

    public void setStatusDef(String statusDef) {
        this.statusDef = statusDef;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}