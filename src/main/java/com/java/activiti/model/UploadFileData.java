package com.java.activiti.model;

public class UploadFileData {

    String originalFileName;

    String fileName;

    long size;

    String type;

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UploadFileData{");
        sb.append("originalFileName='").append(originalFileName).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}
