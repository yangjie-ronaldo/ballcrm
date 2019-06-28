package org.nothink.ballcrm.entity;

import java.util.Arrays;
import java.util.Date;

public class StuTransCriteria extends PagedCriteria {
    private Integer fromCc;
    private Integer toCc;
    private Integer preCc;
    private Boolean hasPlanTodo;
    private Boolean hasScheduleTodo;
    private String name;
    private Date startDate;
    private Date endDate;
    private Integer[] stus;

    @Override
    public String toString() {
        return "StuTransCriteria{" +
                "fromCc=" + fromCc +
                ", toCc=" + toCc +
                ", preCc=" + preCc +
                ", hasPlanTodo=" + hasPlanTodo +
                ", hasScheduleTodo=" + hasScheduleTodo +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", stus=" + Arrays.toString(stus) +
                '}';
    }

    public Integer getFromCc() {
        return fromCc;
    }

    public void setFromCc(Integer fromCc) {
        this.fromCc = fromCc;
    }

    public Integer getToCc() {
        return toCc;
    }

    public void setToCc(Integer toCc) {
        this.toCc = toCc;
    }

    public Integer getPreCc() {
        return preCc;
    }

    public void setPreCc(Integer preCc) {
        this.preCc = preCc;
    }

    public Boolean getHasPlanTodo() {
        return hasPlanTodo;
    }

    public void setHasPlanTodo(Boolean hasPlanTodo) {
        this.hasPlanTodo = hasPlanTodo;
    }

    public Boolean getHasScheduleTodo() {
        return hasScheduleTodo;
    }

    public void setHasScheduleTodo(Boolean hasScheduleTodo) {
        this.hasScheduleTodo = hasScheduleTodo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer[] getStus() {
        return stus;
    }

    public void setStus(Integer[] stus) {
        this.stus = stus;
    }
}
