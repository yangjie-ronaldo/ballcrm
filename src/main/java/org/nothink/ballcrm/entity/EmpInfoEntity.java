package org.nothink.ballcrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmpInfoEntity extends PagedCriteria {
    private Integer eid;
    private String name;
    private String loginid;
    private String pass;
    @JsonIgnore
    private String role;
    private List<EmpRoleRelEntity> roles;
    private Integer nid;
}