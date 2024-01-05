package com.example.cashmanagementmicroservice.entites;

import java.util.List;

public class LogFileInfo {
    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public List<String> getFileName() {
        return fileName;
    }

    public void setFileName(List<String> fileName) {
        this.fileName = fileName;
    }

    public LogFileInfo(String folderName, List<String> fileName) {
        this.folderName = folderName;
        this.fileName = fileName;
    }

    public LogFileInfo() {
        this.folderName = null;
        this.fileName = null;
    }

    String folderName;
    List<String> fileName;
}
