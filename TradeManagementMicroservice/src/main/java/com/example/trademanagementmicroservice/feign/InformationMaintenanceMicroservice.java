package com.example.trademanagementmicroservice.feign;

import com.example.trademanagementmicroservice.entites.ResultCode;
import com.example.trademanagementmicroservice.entites.ReturnRateValue;
import com.example.trademanagementmicroservice.entites.ReturnValue;
import com.example.trademanagementmicroservice.entites.ReturnInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*@FeignClient(name = "InformationMaintenanceMicroservice", path = "/api/v1/informationmaintenance")
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
    @GetMapping("/getFundValue") ReturnValue getFundValue(@RequestParam("fundCode") String fundCode);
}*/

@FeignClient(name = "InformationMaintenanceMicroservice", path = "/api/v1/informationmaintenance")
public interface InformationMaintenanceMicroservice {
    @GetMapping("/Test")
    String testConnectivity();
    @PostMapping("/CheckWorkingHours")
    ResultCode checkWorkingHours(@RequestParam("dateTimeString") String
                                         dateTimeString);
    @PostMapping("/CheckWorkingDay")
    ResultCode checkWorkingDay(@RequestParam("dateTimeString") String
                                       dateTimeString);
    @GetMapping("/ObtainMinPurchaseAmount")
    ReturnRateValue getMinPurchaseAmount(
            @RequestParam("fundCode") String fundCode,
            @RequestParam("requestMod") String requestMod
    );
    @GetMapping("/ObtainSubscriptionFees")
    ReturnValue getSubscriptionFees(
            @RequestParam("fundCode") String fundCode,
            @RequestParam("amount") double amount
    );
    @GetMapping("/ObtainRedemptionFees")
    ReturnValue getRedemptionFees(
            @RequestParam("fundCode") String fundCode,
            @RequestParam("day") double day
    );
    @GetMapping("/ObtainSaleServicesFee")
    ReturnValue getSaleServicesFee(@RequestParam("fundCode") String fundCode);
    @GetMapping("/ObtainManageFee")
    ReturnValue getManageFee(@RequestParam("fundCode") String fundCode);
    @GetMapping("/ObtainCustodyFee")
    ReturnValue getCustodyFee(@RequestParam("fundCode") String fundCode);
    @GetMapping("/ObtainDayMaxBuyAmount")
    ReturnValue getDayMaxBuyAmount(@RequestParam("fundCode") String fundCode);
    @GetMapping("/ObtainFundInfo")
    ReturnInfo getFundInfo(@RequestParam("fundCode") String fundCode);
    @GetMapping("/ObtainFundValue")
    ReturnValue getFundValue(@RequestParam("fundCode") String fundCode);
}
