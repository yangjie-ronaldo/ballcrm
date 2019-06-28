package org.nothink.ballcrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class PagedCriteria {
    // 当前页
    @JsonIgnore
    int currentPage = 1;
    // 每页显示的总条数
    @JsonIgnore
    int pageSize = 10;

    @Override
    public String toString() {
        return "PagedCriteria{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
