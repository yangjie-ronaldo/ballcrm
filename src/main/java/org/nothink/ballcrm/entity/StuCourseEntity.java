package org.nothink.ballcrm.entity;

import java.util.Date;

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

    @Override
    public String toString() {
        return "StuCourseEntity{" +
                "pkid=" + pkid +
                ", sid=" + sid +
                ", eid=" + eid +
                ", eName='" + eName + '\'' +
                ", courseTypeId=" + courseTypeId +
                ", courseName='" + courseName + '\'' +
                ", num=" + num +
                ", fee=" + fee +
                ", createDate=" + createDate +
                ", endDate=" + endDate +
                ", updateDate=" + updateDate +
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

    public Integer getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(Integer courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}