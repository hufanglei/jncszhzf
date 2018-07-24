package com.java.activiti.controller.sysmanage.param;

import com.java.activiti.common.param.SortParam;

/**
 * Created by Administrator on 2017/12/20.
 */
public class SysZdParam {
    private String dmmc;
    private String dmbh;
    private SortParam sortParam;

    public String getDmmc() {
        return dmmc;
    }

    public void setDmmc(String dmmc) {
        this.dmmc = dmmc;
    }

    public String getDmbh() {
        return dmbh;
    }

    public void setDmbh(String dmbh) {
        this.dmbh = dmbh;
    }

    public SortParam getSortParam() {
        return sortParam;
    }

    public void setSortParam(SortParam sortParam) {
        this.sortParam = sortParam;
    }
}
