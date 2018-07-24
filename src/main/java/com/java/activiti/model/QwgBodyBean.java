package com.java.activiti.model;
import java.util.List;

public class QwgBodyBean {

    private int total;
    private int records;
    private int page;
    private List<QwgRowBean> rows;
    public void setTotal(int total) {
         this.total = total;
     }
     public int getTotal() {
         return total;
     }

    public void setRecords(int records) {
         this.records = records;
     }
     public int getRecords() {
         return records;
     }

    public void setPage(int page) {
         this.page = page;
     }
     public int getPage() {
         return page;
     }

    public void setRows(List<QwgRowBean> rows) {
         this.rows = rows;
     }
     public List<QwgRowBean> getRows() {
         return rows;
     }

}