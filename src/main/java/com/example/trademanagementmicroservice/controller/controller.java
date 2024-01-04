package com.example.trademanagementmicroservice.controller;

import com.example.trademanagementmicroservice.entites.BankAccount;
import com.example.trademanagementmicroservice.entites.ResultCode;
import com.example.trademanagementmicroservice.entites.TradeAccount;
import com.example.trademanagementmicroservice.entites.ReturnInfo;
import com.example.trademanagementmicroservice.feign.InformationMaintenanceMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/TradeManagement")
public class controller {
    @Autowired
    InformationMaintenanceMicroservice informationMaintenanceMicroservice;


    @GetMapping("/test")
    public ReturnInfo test() {

        return informationMaintenanceMicroservice.getFundInfo("001167");


    }


}
