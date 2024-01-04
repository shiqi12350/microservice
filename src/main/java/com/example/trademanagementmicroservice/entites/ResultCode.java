package com.example.trademanagementmicroservice.entites;

public class ResultCode {
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    int resultCode;

    public ResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode() {
        this.resultCode = 1;
    }
}
