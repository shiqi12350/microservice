package com.example.trademanagementmicroservice.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.example.trademanagementmicroservice.entites.ResultCode;
import com.example.trademanagementmicroservice.entites.ReturnInfo;
import com.example.trademanagementmicroservice.entites.TradeOrder;
import com.example.trademanagementmicroservice.feign.InformationMaintenanceMicroservice;
import com.example.trademanagementmicroservice.services.Trade;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/TradeManagement")
public class controller {

    @Autowired
    Trade trade;
    @Autowired
    InformationMaintenanceMicroservice informationMaintenanceMicroservice;

    //初始化新交易账户
    @PostMapping("/InitNewTradeAccount")
    public ResultCode InitNewTradeAccount()
    {
        return trade.getNewAccounttoTrade();
    }


    @GetMapping("/test")
    public ReturnInfo test() {

        return informationMaintenanceMicroservice.getFundInfo("001167");
    }

    @GetMapping("/test2")
    public String testConnectivity() {
        try (Entry entry = SphU.entry("common")) {

            return "测试成功！";
        }catch (Exception e){
            return "触发限流";
        }
    }

    //申购
    @PostMapping("/Subscription")
    public TradeOrder Subscription(//String TraderID,String fundID,double tradeAmount,String cardID
                                @RequestParam("TraderID")String TraderID,
                                @RequestParam("fundID")String fundID,
                                @RequestParam("tradeAmount")double tradeAmount,
                                @RequestParam("cardID")String cardID                            )
    {
        try (Entry entry = SphU.entry("common")) {

            return trade.Subscribe(TraderID,fundID,tradeAmount,cardID);
        }catch (Exception e){
            TradeOrder TO=new TradeOrder();
            return TO;
        }

    }

    //申购确认confirmSubscribe
    @PostMapping("/ConfirmSubscribe")
    public ResultCode ConfirmSubscribe()
    {
        return trade.confirmSubscribe();
    }


    //赎回
    @PostMapping("/Redemption")
    public TradeOrder Redemption(//
                                @RequestParam("Ordernum")String Ordernum
    ){
        try (Entry entry = SphU.entry("common")) {

            return trade.Redemption(Ordernum);
        }catch (Exception e){
            TradeOrder TO=new TradeOrder();
            return TO;
        }
    }

    //赎回确认
    @PostMapping("/ConfirmRedemption")
    public ResultCode ConfirmRedemption()
    {
        return trade.confirmRedemption();
    }

    //撤销
    @PostMapping("/CancelTrade")
    public ResultCode CancelTrade(//
                                 @RequestParam("Ordernum")String Ordernum,
                                  @RequestParam("OrderType")String OrderType
    ){
        return trade.cancelTrade(Ordernum,OrderType);
    }

    //发送订单
    @PostMapping("/SendOrder")
    public ResultCode SendOrder(){
        return trade.sendOrder();
    }

    @PostMapping("/OrderConfirm")
    public ResultCode OrderConfirm(){
        return trade.getconfirm();
    }

    @PostMapping("/UpdateFlowRule")
    public ResultCode UpdateFlowRule(@RequestParam("qps") int qps) {
        return trade.updateFlowRule(qps);
    }

    //定义限流功能
    @PostConstruct
    public void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("common");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(1);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
