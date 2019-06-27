package org.nothink.ballcrm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class StatisticsCriteria {
    private Integer nid;
    private Date startDate;
    private Date endDate;
}
