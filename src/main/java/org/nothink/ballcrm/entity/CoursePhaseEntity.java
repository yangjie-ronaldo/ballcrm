package org.nothink.ballcrm.entity;

public class CoursePhaseEntity {
    private Integer pkid;

    private String name;

    public Integer getPkid() {
        return pkid;
    }

    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return "CoursePhaseEntity{" +
                "pkid=" + pkid +
                ", name='" + name + '\'' +
                '}';
    }
}