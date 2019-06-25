package org.nothink.ballcrm.entity;

import lombok.Data;
import java.util.Date;

@Data
public class LoginTokenEntity {
    private Integer eid;
    private String token;
    private Date expired;
    private Integer status;
}