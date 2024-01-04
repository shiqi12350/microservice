package com.example.messagenotificationmicroservice.entites;

import java.util.List;

public class ReturnLogListInfo {
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public List<LogFileInfo> getInfo() {
        return info;
    }

    public void setInfo(List<LogFileInfo> info) {
        this.info = info;
    }

    public ReturnLogListInfo(int resultCode, List<LogFileInfo> info) {
        this.resultCode = resultCode;
        this.info = info;
    }

    public ReturnLogListInfo() {
        this.resultCode = 1;
        this.info = null;
    }

    int resultCode;
    List<LogFileInfo> info;
}
