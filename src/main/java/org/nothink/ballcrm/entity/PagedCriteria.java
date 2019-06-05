package org.nothink.ballcrm.entity;

public abstract class PagedCriteria {
    // 当前页
    int currentPage = 1;
    // 每页显示的总条数
    int pageSize = 10;

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
