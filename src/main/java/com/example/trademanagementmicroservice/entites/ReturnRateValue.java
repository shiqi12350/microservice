package com.example.trademanagementmicroservice.entites;

public class ReturnRateValue {
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ReturnRateValue(int resultCode, double value) {
        this.resultCode = resultCode;
        this.value = value;
    }

    public ReturnRateValue() {
        this.resultCode = 1;
        this.value = 0.0;
    }

    int resultCode;
    double value;
}
