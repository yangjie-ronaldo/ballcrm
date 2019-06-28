package org.nothink.ballcrm.entity;

public class RolesEntity {
    private String role;
    private String name;
    private String desc;

    @Override
    public String toString() {
        return "RolesEntity{" +
                "role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}