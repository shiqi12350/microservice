package com.example.trademanagementmicroservice.feign;

import com.example.trademanagementmicroservice.entites.ResultCode;
import com.example.trademanagementmicroservice.entites.ReturnRateValue;
import com.example.trademanagementmicroservice.entites.ReturnValue;
import com.example.trademanagementmicroservice.entites.ReturnInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "information-maintenance-service", path = "/api/v1/informationmaintenance")
public interface InformationMaintenanceMicroservice {
    @GetMapping("/test")
    String testConnectivity();
    @PostMapping("/checkWorkingHours")
    ResultCode checkWorkingHours(@RequestParam("dateTimeString") String
                                         dateTimeString);
    @PostMapping("/checkWorkingDay")
    ResultCode checkWorkingDay(@RequestParam("dateTimeString") String
                                       dateTimeString);
    @GetMapping("/getMinPurchaseAmount")
    ReturnRateValue getMinPurchaseAmount(
            @RequestParam("fundCode") String fundCode,
            @RequestParam("requestMod") String requestMod
    );
    @GetMapping("/getSubscriptionFees")
    ReturnValue getSubscriptionFees(
            @RequestParam("fundCode") String fundCode,
            @RequestParam("amount") double amount
    );
    @GetMapping("/getRedemptionFees")
    ReturnValue getRedemptionFees(
            @RequestParam("fundCode") String fundCode,
            @RequestParam("day") double day
    );
    @GetMapping("/getSaleServicesFee")
    ReturnValue getSaleServicesFee(@RequestParam("fundCode") String fundCode);
    @GetMapping("/getManageFee")
    ReturnValue getManageFee(@RequestParam("fundCode") String fundCode);
    @GetMapping("/getCustodyFee")
    ReturnValue getCustodyFee(@RequestParam("fundCode") String fundCode);
    @GetMapping("/getDayMaxBuyAmount")
    ReturnValue getDayMaxBuyAmount(@RequestParam("fundCode") String fundCode);
    @GetMapping("/getFundInfo")
    ReturnInfo getFundInfo(@RequestParam("fundCode") String fundCode);
}
