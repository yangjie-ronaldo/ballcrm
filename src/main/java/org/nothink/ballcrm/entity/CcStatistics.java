package org.nothink.ballcrm.entity;

public class CcStatistics {
    private Integer cc;
    private String ccName;
    private Integer newStuNum;
    private Integer demoScheduleNum;
    private Integer demoSignNum;
    private Integer demoCloseNum;
    private Integer buy198Num;
    private Integer myStu198Num;

    public CcStatistics() {
        super();
    }

    public CcStatistics(Integer cc, String ccName, Integer newStuNum, Integer demoScheduleNum, Integer demoSignNum, Integer demoCloseNum, Integer buy198Num, Integer myStu198Num) {
        this.cc = cc;
        this.ccName = ccName;
        this.newStuNum = newStuNum;
        this.demoScheduleNum = demoScheduleNum;
        this.demoSignNum = demoSignNum;
        this.demoCloseNum = demoCloseNum;
        this.buy198Num = buy198Num;
        this.myStu198Num = myStu198Num;
    }

    public Integer getCc() {
        return cc;
    }

    public void setCc(Integer cc) {
        this.cc = cc;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public Integer getNewStuNum() {
        return newStuNum;
    }

    public void setNewStuNum(Integer newStuNum) {
        this.newStuNum = newStuNum;
    }

    public Integer getDemoScheduleNum() {
        return demoScheduleNum;
    }

    public void setDemoScheduleNum(Integer demoScheduleNum) {
        this.demoScheduleNum = demoScheduleNum;
    }

    public Integer getDemoSignNum() {
        return demoSignNum;
    }

    public void setDemoSignNum(Integer demoSignNum) {
        this.demoSignNum = demoSignNum;
    }

    public Integer getDemoCloseNum() {
        return demoCloseNum;
    }

    public void setDemoCloseNum(Integer demoCloseNum) {
        this.demoCloseNum = demoCloseNum;
    }

    public Integer getBuy198Num() {
        return buy198Num;
    }

    public void setBuy198Num(Integer buy198Num) {
        this.buy198Num = buy198Num;
    }

    public Integer getMyStu198Num() {
        return myStu198Num;
    }

    public void setMyStu198Num(Integer myStu198Num) {
        this.myStu198Num = myStu198Num;
    }

    @Override
    public String toString() {
        return "CcStatistics{" +
                "cc=" + cc +
                ", ccName='" + ccName + '\'' +
                ", newStuNum=" + newStuNum +
                ", demoScheduleNum=" + demoScheduleNum +
                ", demoSignNum=" + demoSignNum +
                ", demoCloseNum=" + demoCloseNum +
                ", buy198Num=" + buy198Num +
                ", myStu198Num=" + myStu198Num +
                '}';
    }
}
