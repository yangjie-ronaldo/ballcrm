package org.nothink.ballcrm.entity;

import java.util.Date;

public class StatisticsCriteria {
    private Integer nid;
    private Date startDate;
    private Date endDate;

    @Override
    public String toString() {
        return "StatisticsCriteria{" +
                "nid=" + nid +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
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
