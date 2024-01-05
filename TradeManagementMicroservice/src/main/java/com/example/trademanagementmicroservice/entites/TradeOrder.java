package com.example.trademanagementmicroservice.entites;

import com.example.trademanagementmicroservice.feign.InformationMaintenanceMicroservice;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;


public class TradeOrder {

    public String getOrderNum()
    {
        return this.OrderNum;
    }
    public int getOrderType()
    {
        return this.OrderType;
    }
    public String getTraderID() {return this.TraderID;}
    public String getcardID()
    {
        return this.cardID;
    }
    public String getCompany()
    {
        return this.Company;
    }
    public String getfundID()
    {
        return this.fundID;
    }
    public double getfundAmount()
    {
        return this.fundAmount;
    }
    public double getfundFeeRate()
    {
        return this.fundFeeRate;
    }
    public double getportion()
    {
        return this.portion;
    }
    public int getstate()
    {
        return this.state;
    }
    public String getsubmitDate()
    {
        return this.submitDate;
    }
    public String getconfirmDate()
    {
        return this.confirmDate;
    }

    //申购用，从金额计算份额
    public void setportion(double singlePrize){
        //交易金额去除费率是实际用来换份额的
        double amountAfterFee=this.fundAmount*(1-this.fundFeeRate/100);
        //实际钱数除以单价是实际买到的份额
        this.portion=amountAfterFee/singlePrize;
    }
    //赎回用，从份额计算金额
    public void setfundAmount(double singlePrize){
        //单价*份额=赎回总金额
        double amountBeforeFee=this.portion*singlePrize;
        //实际拿到的金额是去除赎回费的
        this.fundAmount=amountBeforeFee*(1-this.fundFeeRate/100);
    }

    public String setOrderType(int tradeType){
        switch(tradeType){
            case 1: return"1";//申购
            case 2: return"2";//赎回
        }
        return "0";
    }




    public void seteffect(){
        this.state=1;
    }

    //订单记录该笔交易的订单类型、订单编号、交易人id，卡号，基金公司、基金代码、提交金额、费率、对应份额、订单状态、提交时间、确认时间
    int OrderType;
    String OrderNum;
    String TraderID;
    String cardID;
    String Company;
    String fundID;
    double fundAmount;
    double fundFeeRate;
    double portion;
    int state=0;//-1为交易拒绝，0为无效，1为暂时有效，2为已发送，3为已经确认,4为订单已经完成申购/赎回
    String submitDate;
    String confirmDate;

    public TradeOrder()
    {
        state=-1;
    }

    public TradeOrder(int conflict)
    {
        OrderNum="交易限流，下单失败";
        state=-1;
    }

    //申购订单的默认构造函数,订单类型不同，这个传入费用计算份额
    public TradeOrder(String OrderNum,String TraderID,String fundID,double fundAmount,double fundFeeRate,String cardID,int OrderType,String confirmDate){
        this.OrderNum=OrderNum;
        this.OrderType=OrderType;
        this.TraderID=TraderID;
        this.cardID=cardID;
        this.fundID=fundID;
        this.fundAmount=fundAmount;
        this.fundFeeRate=fundFeeRate;
        //确认日需要从下一个交易日算
        this.confirmDate=confirmDate;
        switch(fundID){
            case"001167":this.Company="金鹰公司";
                break;
            case"007994":this.Company="华夏基金";
                break;
            case"003547":this.Company="鹏华基金";
                break;
            case"400030":this.Company="东方基金";
                break;
            case"001937":this.Company="兴银基金";
                break;
            case"002201":this.Company="大成基金";
                break;
        }

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将日期转换为字符串
        String submitDate = currentDate.format(formatter);
        this.submitDate=submitDate;

    }
    //赎回订单的默认构造函数，这个传入份额计算金额
    public TradeOrder(String OrderNum,int OrderType,String TraderID,String fundID,String cardID,double portion,double fundFeeRate,String confirmDate){
        this.OrderNum=OrderNum;
        this.OrderType=OrderType;
        this.TraderID=TraderID;
        this.cardID=cardID;
        this.fundID=fundID;
        this.portion=portion;
        this.fundFeeRate=fundFeeRate;
        //确认日需要从下一个交易日算
        this.confirmDate=confirmDate;
        switch(fundID){
            case"001167":this.Company="金鹰公司";
                break;
            case"007994":this.Company="华夏基金";
                break;
            case"003547":this.Company="鹏华基金";
                break;
            case"400030":this.Company="东方基金";
                break;
            case"001937":this.Company="兴银基金";
                break;
            case"002201":this.Company="大成基金";
                break;
        }

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将日期转换为字符串
        String submitDate = currentDate.format(formatter);
        this.submitDate=submitDate;
    }

}
