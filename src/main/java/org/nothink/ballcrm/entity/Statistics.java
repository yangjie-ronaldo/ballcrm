package org.nothink.ballcrm.entity;

public class Statistics {
    private Integer eid;
    private Integer num;

    @Override
    public String toString() {
        return "Statistics{" +
                "eid=" + eid +
                ", num=" + num +
                '}';
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
