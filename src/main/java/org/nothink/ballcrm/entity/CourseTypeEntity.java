package org.nothink.ballcrm.entity;

public class CourseTypeEntity {
    private Integer pkid;
    private String typeName;
    private String desc;
    private Integer num;

    @Override
    public String toString() {
        return "CourseTypeEntity{" +
                "pkid=" + pkid +
                ", typeName='" + typeName + '\'' +
                ", desc='" + desc + '\'' +
                ", num=" + num +
                '}';
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