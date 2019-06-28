package org.nothink.ballcrm.entity;

import java.util.Date;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
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

    public String getVerifyEName() {
        return verifyEName;
    }

    public void setVerifyEName(String verifyEName) {
        this.verifyEName = verifyEName;
    }

    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "ContactPlanEntity{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", pkid=" + pkid +
                ", planNote='" + planNote + '\'' +
                ", finishNote='" + finishNote + '\'' +
                ", createDate=" + createDate +
                ", planDate=" + planDate +
                ", finishDate=" + finishDate +
                ", sid=" + sid +
                ", sName='" + sName + '\'' +
                ", eid=" + eid +
                ", eName='" + eName + '\'' +
                ", status='" + status + '\'' +
                ", statusDef='" + statusDef + '\'' +
                ", verifyNote='" + verifyNote + '\'' +
                ", verifyEid=" + verifyEid +
                ", verifyEName='" + verifyEName + '\'' +
                ", node=" + node +
                '}';
    }
}
