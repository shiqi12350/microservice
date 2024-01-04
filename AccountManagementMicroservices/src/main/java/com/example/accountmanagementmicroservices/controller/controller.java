package com.example.accountmanagementmicroservices.controller;

import com.example.accountmanagementmicroservices.entites.BankAccount;
import com.example.accountmanagementmicroservices.entites.ResultCode;
import com.example.accountmanagementmicroservices.entites.TradeAccount;
import com.example.accountmanagementmicroservices.feign.MessageNotificationFeignServices;
import com.example.accountmanagementmicroservices.service.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/account")
public class controller {
    @Autowired
    MessageNotificationFeignServices messageNotificationFeignServices;

    @Autowired
    UserInfo userInfo;

    @GetMapping("/Test")
    public String testConnectivity() {
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime timestampNow = ZonedDateTime.ofInstant(Instant.now(), zoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        messageNotificationFeignServices.writeLog(timestampNow.format(formatter), "test", "testfeign", "测试feign服务成功！");
        return "用户信息微服务连接测试成功！";
    }

    @GetMapping("/ObtainNewAccount")
    public TradeAccount getNewAccount(){
        return userInfo.getNewAccount();
    }

    @GetMapping("/BindUserCardNumber")
    public BankAccount bindUserCardNumber(
            @RequestParam("cId")String cId
    ){
        return userInfo.bindUserCardNumber(cId);
    }

    @PostMapping("/OpeningFundAccount")
    public ResultCode openingFundAccount(
            @RequestParam("cardId")String cardId,
            @RequestParam("cId")String cId,
            @RequestParam("companyName")String companyName
    ){
        return userInfo.openingFundAccount(cardId,cId,companyName);
    }


    @PostMapping("/ConfirmTransaction")
    public ResultCode confirmTransaction(
            @RequestParam String companyName,
            @RequestParam String cId,
            @RequestParam String cardId,
            @RequestParam String fundId,
            @RequestParam double changeInShares) {
        return userInfo.confirmTransaction(companyName,cId,cardId,fundId,changeInShares);
    }

}
