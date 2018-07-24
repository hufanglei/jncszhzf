package com.java.activiti.model;

import java.io.Serializable;

public class TMainIssueKhBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id; // 自增主键
    private String qhzdId; // 机构id
    private String qhzdName; // 机构名称
    private String zxcl; // 自行处理
    private String ajbz; // 案件比重
    private String ascl;//按时处理
    private String kscl;//快速处理
    private String wzxgfx;//完整性、规范性
    private String ffts;//反复投诉
    private String dbwz;//督办问责
    private String gzxt;//工作协调
    private String sljb;//受理交办
    private String xcjz;//巡查机制
    private String xcbd;//宣传报道
    private String jdjc;//监督检查
    private String khyf;//考核月份
    private String khsj;//考核时间
    private String BmType;//街道委办局类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQhzdId() {
        return qhzdId;
    }

    public void setQhzdId(String qhzdId) {
        this.qhzdId = qhzdId;
    }

    public String getZxcl() {
        return zxcl;
    }

    public void setZxcl(String zxcl) {
        this.zxcl = zxcl;
    }

    public String getAjbz() {
        return ajbz;
    }

    public void setAjbz(String ajbz) {
        this.ajbz = ajbz;
    }

    public String getAscl() {
        return ascl;
    }

    public void setAscl(String ascl) {
        this.ascl = ascl;
    }

    public String getKscl() {
        return kscl;
    }

    public void setKscl(String kscl) {
        this.kscl = kscl;
    }

    public String getWzxgfx() {
        return wzxgfx;
    }

    public void setWzxgfx(String wzxgfx) {
        this.wzxgfx = wzxgfx;
    }

    public String getFfts() {
        return ffts;
    }

    public void setFfts(String ffts) {
        this.ffts = ffts;
    }

    public String getDbwz() {
        return dbwz;
    }

    public void setDbwz(String dbwz) {
        this.dbwz = dbwz;
    }

    public String getGzxt() {
        return gzxt;
    }

    public void setGzxt(String gzxt) {
        this.gzxt = gzxt;
    }

    public String getSljb() {
        return sljb;
    }

    public void setSljb(String sljb) {
        this.sljb = sljb;
    }

    public String getXcjz() {
        return xcjz;
    }

    public void setXcjz(String xcjz) {
        this.xcjz = xcjz;
    }

    public String getXcbd() {
        return xcbd;
    }

    public void setXcbd(String xcbd) {
        this.xcbd = xcbd;
    }

    public String getJdjc() {
        return jdjc;
    }

    public void setJdjc(String jdjc) {
        this.jdjc = jdjc;
    }

    public String getKhyf() {
        return khyf;
    }

    public void setKhyf(String khyf) {
        this.khyf = khyf;
    }

    public String getKhsj() {
        return khsj;
    }

    public void setKhsj(String khsj) {
        this.khsj = khsj;
    }

    public String getQhzdName() {
        return qhzdName;
    }

    public void setQhzdName(String qhzdName) {
        this.qhzdName = qhzdName;
    }

    public String getBmType() {
        return BmType;
    }

    public void setBmType(String bmType) {
        BmType = bmType;
    }
}
