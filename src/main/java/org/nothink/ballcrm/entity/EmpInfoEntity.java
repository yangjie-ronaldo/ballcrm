package org.nothink.ballcrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public class EmpInfoEntity extends PagedCriteria {
    private Integer eid;
    private String name;
    private String loginid;
    @JsonIgnore
    private String pass;
    @JsonIgnore
    private String role;
    private List<EmpRoleRelEntity> roles;
    private Integer nid;
    private String nodeName;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<EmpRoleRelEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<EmpRoleRelEntity> roles) {
        this.roles = roles;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    @Override
    public String toString() {
        return "EmpInfoEntity{" +
                "eid=" + eid +
                ", name='" + name + '\'' +
                ", loginid='" + loginid + '\'' +
                ", pass='" + pass + '\'' +
                ", role='" + role + '\'' +
                ", roles=" + roles +
                ", nid=" + nid +
                '}';
    }
}