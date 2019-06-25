package org.nothink.ballcrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public abstract class PagedCriteria {
    // 当前页
    @JsonIgnore
    int currentPage = 1;
    // 每页显示的总条数
    @JsonIgnore
    int pageSize = 10;
}
