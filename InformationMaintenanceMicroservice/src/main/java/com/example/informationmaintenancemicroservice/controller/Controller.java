package com.example.informationmaintenancemicroservice.controller;

import com.example.informationmaintenancemicroservice.entites.ResultCode;
import com.example.informationmaintenancemicroservice.entites.ReturnInfo;
import com.example.informationmaintenancemicroservice.entites.ReturnRateValue;
import com.example.informationmaintenancemicroservice.entites.ReturnValue;
import com.example.informationmaintenancemicroservice.services.BasicServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/informationmaintenance")
public class Controller {

    @Autowired
    BasicServices basicServices;

    @GetMapping("/Test")
    public String testConnectivity() {
        return "信息维护微服务链接性测试成功！";
    }

    @PostMapping("/CheckWorkingHours")
    public ResultCode checkWorkingHours(@RequestParam("dateTimeString")String dateTimeString){
        return basicServices.checkWorkingHours(dateTimeString);
    }

    @PostMapping("/CheckWorkingDay")
    public ResultCode checkWorkingDay(@RequestParam("dateTimeString")String dateTimeString){
        return basicServices.checkWorkingHours(dateTimeString+"-10-0-0");
    }

    @GetMapping("/ObtainMinPurchaseAmount")
    public ReturnRateValue getMinPurchaseAmount(
            @RequestParam("fundCode")String fundCode,
            @RequestParam("requestMod")String requestMod
    ){
        return basicServices.getMinPurchaseAmount(fundCode,requestMod);
    }

    @GetMapping("/ObtainSubscriptionFees")
    public ReturnValue getSubscriptionFees(
            @RequestParam("fundCode")String fundCode,
            @RequestParam("amount") double amount
    ){
        return basicServices.getSubscriptionFees(fundCode,amount);
    }

    @GetMapping("/ObtainRedemptionFees")
    public ReturnValue getRedemptionFees(
            @RequestParam("fundCode")String fundCode,
            @RequestParam("day") double day
    ){
        return basicServices.getRedemptionFees(fundCode,day);
    }

    @GetMapping("/ObtainSaleServicesFee")
    public ReturnValue getSaleServicesFee(
            @RequestParam("fundCode")String fundCode
    ){
        return basicServices.getSaleServicesFee(fundCode);
    }

    @GetMapping("/ObtainManageFee")
    public ReturnValue getManageFee(
            @RequestParam("fundCode")String fundCode
    ){
        return basicServices.getManageFee(fundCode);
    }

    @GetMapping("/ObtainCustodyFee")
    public ReturnValue getCustodyFee(
            @RequestParam("fundCode")String fundCode
    ){
        return basicServices.getCustodyFee(fundCode);
    }

    @GetMapping("/ObtainDayMaxBuyAmount")
    public ReturnValue getDayMaxBuyAmount(
            @RequestParam("fundCode")String fundCode
    ){
        return basicServices.getDayMaxBuyAmount(fundCode);
    }

    @GetMapping("/ObtainFundInfo")
    public ReturnInfo getFundInfo(
            @RequestParam("fundCode")String fundCode
    ){
        return basicServices.getFundInfo(fundCode);
    }

    @GetMapping("/ObtainFundValue")
    public ReturnValue getFundValue(
            @RequestParam("fundCode")String fundCode
    ){
        return basicServices.getFundValue(fundCode);
    }


}
