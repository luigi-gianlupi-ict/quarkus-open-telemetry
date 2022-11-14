package it.ictgroup.auth;


import java.util.List;
import java.util.Map;

public class PaginatedListWrapper<T> {
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalResults;
    private String sortFields;
    private String sortDirections;
    private Map<String, Object> params;
    private List<T> list;

    public PaginatedListWrapper() {
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer var1) {
        this.currentPage = var1;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer var1) {
        this.pageSize = var1;
    }

    public Integer getTotalResults() {
        return this.totalResults;
    }

    public void setTotalResults(Integer var1) {
        this.totalResults = var1;
    }

    public String getSortFields() {
        return this.sortFields;
    }

    public void setSortFields(String var1) {
        this.sortFields = var1;
    }

    public String getSortDirections() {
        return this.sortDirections;
    }

    public void setSortDirections(String var1) {
        this.sortDirections = var1;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> var1) {
        this.params = var1;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> var1) {
        this.list = var1;
    }
}
