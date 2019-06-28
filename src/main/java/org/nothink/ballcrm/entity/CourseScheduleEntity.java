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
    private Integer traceEid;
    private String traceEName;
    private Integer closeEid;
    private String closeEname;
    private Date createDate;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
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

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
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

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }

    public Integer getTeachEid() {
        return teachEid;
    }

    public void setTeachEid(Integer teachEid) {
        this.teachEid = teachEid;
    }

    public String getTeacheName() {
        return teacheName;
    }

    public void setTeacheName(String teacheName) {
        this.teacheName = teacheName;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public String getNotifyStatusDef() {
        return notifyStatusDef;
    }

    public void setNotifyStatusDef(String notifyStatusDef) {
        this.notifyStatusDef = notifyStatusDef;
    }

    public String getNotifyNote() {
        return notifyNote;
    }

    public void setNotifyNote(String notifyNote) {
        this.notifyNote = notifyNote;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public String getSignStatusDef() {
        return signStatusDef;
    }

    public void setSignStatusDef(String signStatusDef) {
        this.signStatusDef = signStatusDef;
    }

    public String getTraceStatus() {
        return traceStatus;
    }

    public void setTraceStatus(String traceStatus) {
        this.traceStatus = traceStatus;
    }

    public String getTraceStatusDef() {
        return traceStatusDef;
    }

    public void setTraceStatusDef(String traceStatusDef) {
        this.traceStatusDef = traceStatusDef;
    }

    public String getTraceNote() {
        return traceNote;
    }

    public void setTraceNote(String traceNote) {
        this.traceNote = traceNote;
    }

    public Integer getTraceEid() {
        return traceEid;
    }

    public void setTraceEid(Integer traceEid) {
        this.traceEid = traceEid;
    }

    public String getTraceEName() {
        return traceEName;
    }

    public void setTraceEName(String traceEName) {
        this.traceEName = traceEName;
    }

    public Integer getCloseEid() {
        return closeEid;
    }

    public void setCloseEid(Integer closeEid) {
        this.closeEid = closeEid;
    }

    public String getCloseEname() {
        return closeEname;
    }

    public void setCloseEname(String closeEname) {
        this.closeEname = closeEname;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "CourseScheduleEntity{" +
                "eid=" + eid +
                ", nid=" + nid +
                ", pkid=" + pkid +
                ", sid=" + sid +
                ", sName='" + sName + '\'' +
                ", bookingDate=" + bookingDate +
                ", courseTypeId=" + courseTypeId +
                ", courseTypeName='" + courseTypeName + '\'' +
                ", teachEid=" + teachEid +
                ", teacheName='" + teacheName + '\'' +
                ", notifyStatus='" + notifyStatus + '\'' +
                ", notifyStatusDef='" + notifyStatusDef + '\'' +
                ", notifyNote='" + notifyNote + '\'' +
                ", signStatus='" + signStatus + '\'' +
                ", signStatusDef='" + signStatusDef + '\'' +
                ", traceStatus='" + traceStatus + '\'' +
                ", traceStatusDef='" + traceStatusDef + '\'' +
                ", traceNote='" + traceNote + '\'' +
                ", traceEid=" + traceEid +
                ", traceEName='" + traceEName + '\'' +
                ", closeEid=" + closeEid +
                ", closeEname='" + closeEname + '\'' +
                ", createDate=" + createDate +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }
}