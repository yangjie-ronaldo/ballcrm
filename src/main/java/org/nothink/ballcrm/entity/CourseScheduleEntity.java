package org.nothink.ballcrm.entity;

import java.util.Date;

public class CourseScheduleEntity extends PagedCriteria {
    private Integer eid;//查询条件 查员工名下
    private Integer nid;// 查机构的明日待上课

    private Integer pkid;

    private Integer sid;
    private String sName;

    private Date bookingDate;

    private Integer courseTypeId;
    private String courseTypeName;

    private Integer teachEid;
    private String teacheName;

    private String notifyStatus;
    private String notifyStatusDef;

    private String notifyNote;

    private String signStatus;
    private String signStatusDef;

    private String traceStatus;
    private String traceStatusDef;

    private String traceNote;

    private Date createDate;

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }

    public String getTeacheName() {
        return teacheName;
    }

    public void setTeacheName(String teacheName) {
        this.teacheName = teacheName;
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

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Integer getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(Integer courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public Integer getTeachEid() {
        return teachEid;
    }

    public void setTeachEid(Integer teachEid) {
        this.teachEid = teachEid;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus == null ? null : notifyStatus.trim();
    }

    public String getNotifyNote() {
        return notifyNote;
    }

    public void setNotifyNote(String notifyNote) {
        this.notifyNote = notifyNote == null ? null : notifyNote.trim();
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus == null ? null : signStatus.trim();
    }

    public String getTraceStatus() {
        return traceStatus;
    }

    public void setTraceStatus(String traceStatus) {
        this.traceStatus = traceStatus == null ? null : traceStatus.trim();
    }

    public String getTraceNote() {
        return traceNote;
    }

    public void setTraceNote(String traceNote) {
        this.traceNote = traceNote == null ? null : traceNote.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNotifyStatusDef() {
        return notifyStatusDef;
    }

    public void setNotifyStatusDef(String notifyStatusDef) {
        this.notifyStatusDef = notifyStatusDef;
    }

    public String getSignStatusDef() {
        return signStatusDef;
    }

    public void setSignStatusDef(String signStatusDef) {
        this.signStatusDef = signStatusDef;
    }

    public String getTraceStatusDef() {
        return traceStatusDef;
    }

    public void setTraceStatusDef(String traceStatusDef) {
        this.traceStatusDef = traceStatusDef;
    }

    @Override
    public String toString() {
        return "CourseScheduleEntity{" +
                "pkid=" + pkid +
                ", sid=" + sid +
                ", bookingDate=" + bookingDate +
                ", courseTypeId=" + courseTypeId +
                ", teachEid=" + teachEid +
                ", notifyStatus='" + notifyStatus + '\'' +
                ", notifyStatusDef='" + notifyStatusDef + '\'' +
                ", notifyNote='" + notifyNote + '\'' +
                ", signStatus='" + signStatus + '\'' +
                ", signStatusDef='" + signStatusDef + '\'' +
                ", traceStatus='" + traceStatus + '\'' +
                ", traceStatusDef='" + traceStatusDef + '\'' +
                ", traceNote='" + traceNote + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}