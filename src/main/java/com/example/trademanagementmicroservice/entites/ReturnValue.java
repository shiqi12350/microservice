package com.example.trademanagementmicroservice.entites;

public class ReturnValue {
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
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

    public ReturnValue(int resultCode, int valueType, double value) {
        this.resultCode = resultCode;
        this.valueType = valueType;
        this.value = value;
    }

    public ReturnValue() {
        this.resultCode = 1;
        this.valueType = 0;
        this.value = 0;
    }


    int resultCode;
    int valueType;
    double value;
}
