package com.example.informationmaintenancemicroservice.entites;

public class SubscriptionFee {
    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public double getLowLine() {
        return lowLine;
    }

    public void setLowLine(double lowLine) {
        this.lowLine = lowLine;
    }

    public double getHighLine() {
        return highLine;
    }

    public void setHighLine(double highLine) {
        this.highLine = highLine;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public SubscriptionFee(String fundCode, double lowLine, double highLine, int valueType, double value) {
        this.fundCode = fundCode;
        this.lowLine = lowLine;
        this.highLine = highLine;
        this.valueType = valueType;
        this.value = value;
    }


    public SubscriptionFee() {
        this.fundCode = null;
        this.lowLine = 0;
        this.highLine = 0;
        this.valueType = 0;
        this.value = 0;
    }

    String fundCode;
    double lowLine;
    double highLine;
    int valueType;
    double value;
}
