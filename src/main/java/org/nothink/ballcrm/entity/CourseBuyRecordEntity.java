package org.nothink.ballcrm.entity;

import java.util.Date;

public class CourseBuyRecordEntity {
    private Integer pkid;

    private Integer sid;

    private Integer eid;

    private Integer courseTypeId;

    private Integer fee;

    private Date createDate;
    private String note;
    private Integer teacherId;
    private Integer hasMarket;

    @Override
    public String toString() {
        return "CourseBuyRecordEntity{" +
                "pkid=" + pkid +
                ", sid=" + sid +
                ", eid=" + eid +
                ", courseTypeId=" + courseTypeId +
                ", fee=" + fee +
                ", createDate=" + createDate +
                ", note='" + note + '\'' +
                ", teacherId=" + teacherId +
                ", hasMarket=" + hasMarket +
                '}';
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getHasMarket() {
        return hasMarket;
    }

    public void setHasMarket(Integer hasMarket) {
        this.hasMarket = hasMarket;
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

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(Integer courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}