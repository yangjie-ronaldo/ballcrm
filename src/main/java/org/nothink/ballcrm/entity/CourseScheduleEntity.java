package org.nothink.ballcrm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class CourseScheduleEntity extends PagedCriteria {
    private Integer eid;//查询条件 查员工名下
    private Integer nid;// 查机构的明日待上课
    private Integer pkid;
    private Integer sid;
    private String sName;
    private Date bookingDate;
    private Integer courseTypeId;
    private String courseTypeName;
    private Integer teachEid;
    private String teacheName;
    private String notifyStatus;
    private String notifyStatusDef;
    private String notifyNote;
    private String signStatus;
    private String signStatusDef;
    private String traceStatus;
    private String traceStatusDef;
    private String traceNote;
    private Date createDate;
}