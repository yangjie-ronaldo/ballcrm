package org.nothink.ballcrm.entity;

public class CourseTypeEntity {
    private Integer pkid;
    private String typeName;
    private String desc;
    private Integer num;
    private Integer fee;
    private Integer validDay;
    private Integer phaseId;
    private String phaseName;

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getValidDay() {
        return validDay;
    }

    public void setValidDay(Integer validDay) {
        this.validDay = validDay;
    }

    public Integer getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Integer phaseId) {
        this.phaseId = phaseId;
    }

    @Override
    public String toString() {
        return "CourseTypeEntity{" +
                "pkid=" + pkid +
                ", typeName='" + typeName + '\'' +
                ", desc='" + desc + '\'' +
                ", num=" + num +
                ", fee=" + fee +
                ", validDay=" + validDay +
                ", phaseId=" + phaseId +
                ", phaseName='" + phaseName + '\'' +
                '}';
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public Integer getPkid() {
        return pkid;
    }

    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}