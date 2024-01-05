package com.example.trademanagementmicroservice.entites;

public class RedemptionFee {
    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public int getLowDay() {
        return lowDay;
    }

    public void setLowDay(int lowDay) {
        this.lowDay = lowDay;
    }

    public int getHighDay() {
        return highDay;
    }

    public void setHighDay(int highDay) {
        this.highDay = highDay;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public RedemptionFee(String fundCode, int lowDay, int highDay, double value) {
        this.fundCode = fundCode;
        this.lowDay = lowDay;
        this.highDay = highDay;
        this.value = value;
    }

    public RedemptionFee() {
        this.fundCode = null;
        this.lowDay = 0;
        this.highDay = 0;
        this.value = 0.0;
    }

    String fundCode;
    int lowDay;
    int highDay;
    double value;
}
