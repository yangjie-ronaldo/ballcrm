package org.nothink.ballcrm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class StuTransCriteria extends PagedCriteria {
    private Integer fromCc;
    private Integer toCc;
    private Integer preCc;
    private Boolean hasPlanTodo;
    private Boolean hasScheduleTodo;
    private Date startDate;
    private Date endDate;
    private Integer[] stus;
}
