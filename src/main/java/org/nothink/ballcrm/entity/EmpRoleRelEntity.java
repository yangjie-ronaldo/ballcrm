package org.nothink.ballcrm.entity;

public class EmpRoleRelEntity {
    private Integer eid;
    private String role;
    private String roleName;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "EmpRoleRelEntity{" +
                "eid=" + eid +
                ", role='" + role + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}