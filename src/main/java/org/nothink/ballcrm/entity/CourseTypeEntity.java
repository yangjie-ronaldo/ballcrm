package org.nothink.ballcrm.entity;

import lombok.Data;

@Data
public class CourseTypeEntity {
    private Integer pkid;
    private String typeName;
    private String desc;
    private Integer num;
}