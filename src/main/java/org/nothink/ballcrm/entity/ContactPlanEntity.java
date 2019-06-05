package org.nothink.ballcrm.entity;

import java.util.Date;

public class ContactPlanEntity {
    private Integer pkid;

    private String planNote;

    private Date createDate;

    private Date planDate;

    private Integer sid;

    private Integer eid;

    private String status;
    private String statusDef;

    private String verifyStatus;
    private String verifyStautsDef;

    private String verifyNote;

    private Integer verifyEid;

    public String getStatusDef() {
        return statusDef;
    }

    public void setStatusDef(String statusDef) {
        this.statusDef = statusDef;
    }

    public Integer getPkid() {
        return pkid;
    }

    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    public String getPlanNote() {
        return planNote;
    }

    public void setPlanNote(String planNote) {
        this.planNote = planNote == null ? null : planNote.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getVerifyNote() {
        return verifyNote;
    }

    public void setVerifyNote(String verifyNote) {
        this.verifyNote = verifyNote;
    }

    public Integer getVerifyEid() {
        return verifyEid;
    }

    public void setVerifyEid(Integer verifyEid) {
        this.verifyEid = verifyEid;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getVerifyStautsDef() {
        return verifyStautsDef;
    }

    public void setVerifyStautsDef(String verifyStautsDef) {
        this.verifyStautsDef = verifyStautsDef;
    }
}