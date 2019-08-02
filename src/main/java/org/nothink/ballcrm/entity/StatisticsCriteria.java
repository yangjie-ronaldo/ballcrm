package org.nothink.ballcrm.entity;

import java.util.Date;

public class StatisticsCriteria {
    private Integer nid;
    private Date startDate;
    private Date endDate;
    private Integer eid;
    private String key;
    private String status;
    private Integer courseTypeId;
    private Integer hasMarket;

    @Override
    public String toString() {
        return "StatisticsCriteria{" +
                "nid=" + nid +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", eid=" + eid +
                ", key='" + key + '\'' +
                ", status='" + status + '\'' +
                ", courseTypeId=" + courseTypeId +
                ", hasMarket=" + hasMarket +
                '}';
    }

    public Integer getHasMarket() {
        return hasMarket;
    }

    public void setHasMarket(Integer hasMarket) {
        this.hasMarket = hasMarket;
    }

    public Integer getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(Integer courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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
}
