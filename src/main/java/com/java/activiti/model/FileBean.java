package com.java.activiti.model;


public class FileBean {
    private String fileName;

    private String physicsFullFileName;

    private String domainType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPhysicsFullFileName() {
        return physicsFullFileName;
    }

    public void setPhysicsFullFileName(String physicsFullFileName) {
        this.physicsFullFileName = physicsFullFileName;
    }

    public String getDomainType() {
        return domainType;
    }

    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }
}
