package org.nothink.ballcrm.entity;

import java.util.Date;

public class ContactPlanEntity extends PagedCriteria {
    private Integer pkid;

    private String planNote;

    private String finishNote;

    private Date createDate;

    private Date planDate;

    private Date finishDate;

    private Integer sid;

    private Integer eid;

    private String status;
    private String statusDef;

    private String verifyNote;

    private Integer verifyEid;

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
        this.planNote = planNote;
    }

    public String getFinishNote() {
        return finishNote;
    }

    public void setFinishNote(String finishNote) {
        this.finishNote = finishNote;
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

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
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
        this.status = status;
    }

    public String getStatusDef() {
        return statusDef;
    }

    public void setStatusDef(String statusDef) {
        this.statusDef = statusDef;
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
}