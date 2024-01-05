package com.example.trademanagementmicroservice.feign;

import com.example.trademanagementmicroservice.entites.ResultCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@FeignClient(name = "CashManagementMicroservice", path = "/api/v1/cashmanagement")
public interface CashManagementMicroservice {
  /*  //////////// lyy///////////////////////
    // 生成一个没有用过的订单号
    @GetMapping("/newTradeID")
    public String newTradeID();

    // 添加订单
    @PostMapping("/addorder")
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
                               @RequestParam String ConfirmTime) ;

    // 通过订单号查询订单
    @GetMapping("/getorder")
    public Map<String, Object> getorder(@RequestParam String ordertype ,@RequestParam String ordernumber);

    //通过订单号修改订单状态
    @PostMapping("/changestate")
    public ResultCode changestate(@RequestParam String ordertype ,@RequestParam String ordernumber, @RequestParam String orderstate);

    //接口：资金表1/2都是传入账户号 传入+/- 传入数额 能修改资金

    @PostMapping("/changecash1")
    public ResultCode changecash1(@RequestParam String accountid,@RequestParam String SuborAdd, @RequestParam String cashnum);

    @PostMapping("/changecash2")
    public ResultCode changecash2(@RequestParam String accountid, @RequestParam String SuborAdd, @RequestParam String cashnum);

    //每日订单发送
    @PostMapping("/dailysend")
    public ResultCode dailysend();

    //每日订单确认
    @PostMapping("/dailyconfirm")
    public ResultCode dailyconfirm(@RequestParam String confirmdate);

    @GetMapping("/getallnum")
    public List<String> getAllOrderNumbers(@RequestParam String datetime, @RequestParam String ordertype);

    @PostMapping("/addcash2")
    public ResultCode addcash2(@RequestParam String accountid, @RequestParam Double cash);*/

    //////////// lyy///////////////////////
    // 生成一个没有用过的订单号
    @GetMapping("/NewTradeID")
    public String NewTradeID();

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
                               @RequestParam String ConfirmTime) ;

    // 通过订单号查询订单
    @GetMapping("/FindOrder")
    public Map<String, Object> FindOrder(@RequestParam String ordertype ,@RequestParam String ordernumber);

    //通过订单号修改订单状态
    @PostMapping("/ChangeState")
    public ResultCode ChangeState(@RequestParam String ordertype ,@RequestParam String ordernumber, @RequestParam String orderstate);

    //接口：资金表1/2都是传入账户号 传入+/- 传入数额 能修改资金

    @PostMapping("/ChangeCash1")
    public ResultCode ChangeCash1(@RequestParam String accountid,@RequestParam String SuborAdd, @RequestParam String cashnum);

    @PostMapping("/ChangeCash2")
    public ResultCode ChangeCash2(@RequestParam String accountid,@RequestParam String SuborAdd, @RequestParam String cashnum);

    //每日订单发送
    @PostMapping("/DailySend")
    public ResultCode DailySend();

    //每日订单确认
    @PostMapping("/DailyConfirm")
    public ResultCode DailyConfirm(@RequestParam String confirmdate) ;

    //输入日期和订单类型 返回对应日期的所有订单编号
    @GetMapping("/ListOrderNumber")
    public List<String> ListOrderNumber(@RequestParam String datetime, @RequestParam String ordertype);

    @PostMapping("/AddCash2")
    public ResultCode AddCash2(@RequestParam String accountid, @RequestParam Double cash) ;

}
