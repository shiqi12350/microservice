package com.example.informationmaintenancemicroservice.entites;

public class ReturnInfo {
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ReturnInfo(int resultCode, String info) {
        this.resultCode = resultCode;
        this.info = info;
    }

    public ReturnInfo() {
        this.resultCode = 0;
        this.info = null;
    }

    int resultCode;
    String info;
}
