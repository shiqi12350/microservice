package com.example.trademanagementmicroservice.entites;

public class TradeOrder {




    String fundName;
    String submitTime;
    int fundNum;
    String fundFeeType;
    double fundFeeAmount;
    double tradeOrderSumAmount;
    boolean is_effect;
    boolean tradeSuccess;

    public TradeOrder(String fundName, String submitTime,int fundNum){
        this.fundName=fundName;
        this.submitTime=submitTime;
        this.fundNum=fundNum;
        //this.fundFeeType=

    }
}
