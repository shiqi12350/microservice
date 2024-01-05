package com.example.cashmanagementmicroservice.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.example.cashmanagementmicroservice.entites.ResultCode;
import com.example.cashmanagementmicroservice.entites.ReturnLogListInfo;
import com.example.cashmanagementmicroservice.entites.orderinfo;
import com.example.cashmanagementmicroservice.services.EmailService;
import com.example.cashmanagementmicroservice.services.LoggerService;
import com.example.cashmanagementmicroservice.services.PublicService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/cashmanagement")
public class controller {

    @Autowired
    private EmailService emailService;
    @Autowired
    private LoggerService loggerService;
    @Autowired
    private PublicService publicService;

    //////////// lyy///////////////////////
    // 生成一个没有用过的订单号
    @GetMapping("/NewTradeID")
    public String NewTradeID() {
        return publicService.getnewordernum();
    }

    // 添加订单
    @PostMapping("/AddOrder")
    public ResultCode AddOrder(@RequestParam String ordertype,
            @RequestParam String ordernumber,
            @RequestParam String TradeManid,
            @RequestParam String Cardid,
            @RequestParam String company,
            @RequestParam String fundid,
            @RequestParam String SubmissionAmount,
            @RequestParam Double Rate,
            @RequestParam Double share,
            @RequestParam String orderstate,
            @RequestParam String SubmissionTime,
            @RequestParam String ConfirmTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate submissionTime = LocalDate.parse(SubmissionTime, formatter);
        LocalDate confirmTime = LocalDate.parse(ConfirmTime, formatter);
        return publicService.add_an_order(ordertype, ordernumber, TradeManid, Cardid, company, fundid,
                SubmissionAmount, Rate, share, orderstate, submissionTime, confirmTime);

    }

    // 通过订单号查询订单
    @GetMapping("/FindOrder")
    public Map<String, Object> FindOrder(@RequestParam String ordertype ,@RequestParam String ordernumber) {
        return publicService.Getorder(ordertype,ordernumber);
    }

    //通过订单号修改订单状态
    @PostMapping("/ChangeState")
    public ResultCode ChangeState(@RequestParam String ordertype ,@RequestParam String ordernumber, @RequestParam String orderstate) {
        ResultCode rt = new ResultCode();
        publicService.Changestate(ordertype,ordernumber,orderstate);
        return rt;
    }

    //接口：资金表1/2都是传入账户号 传入+/- 传入数额 能修改资金 

    @PostMapping("/ChangeCash1")
    public ResultCode ChangeCash1(@RequestParam String accountid,@RequestParam String SuborAdd, @RequestParam String cashnum) {
        ResultCode rt = new ResultCode();
        publicService.Changecash1(accountid,SuborAdd,cashnum);
        return rt;
    }

    @PostMapping("/ChangeCash2")
    public ResultCode ChangeCash2(@RequestParam String accountid,@RequestParam String SuborAdd, @RequestParam String cashnum) {
        ResultCode rt = new ResultCode();
        publicService.Changecash2(accountid,SuborAdd,cashnum);
        return rt;
    }

    //每日订单发送
    @PostMapping("/DailySend")
    public ResultCode DailySend() {
        ResultCode rt = new ResultCode();
        publicService.Dailysend();
        return rt;
    }

    //每日订单确认
    @PostMapping("/DailyConfirm")
    public ResultCode DailyConfirm(@RequestParam String confirmdate) {
        ResultCode rt = new ResultCode();
        publicService.Dailyconfirm(confirmdate);
        return rt;
    }

    //输入日期和订单类型 返回对应日期的所有订单编号
    @GetMapping("/ListOrderNumber")
    public List<String> ListOrderNumber(@RequestParam String datetime, @RequestParam String ordertype) {
        return publicService.getAllOrderNumbersByDateAndType(datetime, ordertype);
    }

    @PostMapping("/AddCash2")
    public ResultCode AddCash2(@RequestParam String accountid, @RequestParam Double cash) {
        ResultCode rt = new ResultCode();
        publicService.Addcash2(accountid,cash);
        return rt;
    }

}