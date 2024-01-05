package com.example.cashmanagementmicroservice.entites;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
public class orderinfo {

    
    String ordertype;
    int ordernumber;
    String TradeManid;
    String Cardid;    
    String company;
    String fundid;
    String SubmissionAmount;
    Double Rate;
    Double share;
    String orderstate;
    LocalDate SubmissionTime;
    LocalDate ConfirmTime;


    public orderinfo(String ordertype,int ordernumber,String TradeManid,String Cardid,
    String company,String fundid,String SubmissionAmount,Double Rate,Double share,String orderstate,LocalDate SubmissionTime,LocalDate ConfirmTime) {
        this.ordertype = ordertype;
        this.ordernumber = ordernumber;
        this.TradeManid = TradeManid;
        this.Cardid = Cardid;
        this.company = company;
        this.fundid = fundid;
        this.SubmissionAmount = SubmissionAmount;
        this.Rate = Rate;
        this.share = share;
        this.orderstate = orderstate;
        this.SubmissionTime = SubmissionTime;
        this.ConfirmTime = ConfirmTime;
    }

    public orderinfo() {
        this.ordertype = null;
        this.ordernumber = 0;
        this.TradeManid = null;
        this.Cardid = null;
        this.company = null;
        this.fundid = null;
        this.SubmissionAmount = null;
        this.Rate = null;
        this.share = null;
        this.orderstate = null;
        this.SubmissionTime = null;
        this.ConfirmTime = null;
    }

}
