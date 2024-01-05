package com.example.trademanagementmicroservice.feign;

import com.example.trademanagementmicroservice.entites.BankAccount;
import com.example.trademanagementmicroservice.entites.ResultCode;
import com.example.trademanagementmicroservice.entites.TradeAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*@FeignClient(name = "AccountManagementMicroservice",path = "/api/v1/account")
public interface AccountFeignClient {
    @GetMapping("/test")
    String testConnectivity();
    @GetMapping("/getNewAccount")
    TradeAccount getNewAccount();
    @GetMapping("/bindUserCardNumber")
    BankAccount bindUserCardNumber(@RequestParam("cId") String cId);
    @PostMapping("/openingFundAccount")
    ResultCode openingFundAccount(
            @RequestParam("cardId") String cardId,
            @RequestParam("cId") String cId,
            @RequestParam("companyName") String companyName
    );
    @PostMapping("/confirmTransaction")
    ResultCode confirmTransaction(
            @RequestParam String companyName,
            @RequestParam String cId,
            @RequestParam String cardId,
            @RequestParam String fundId,
            @RequestParam double changeInShares
    );
}*/

@FeignClient(name = "AccountManagementMicroservice",path = "/api/v1/account")
public interface AccountFeignClient {

    @GetMapping("/Test")
    String testConnectivity();

    @GetMapping("/ObtainNewAccount")
    TradeAccount getNewAccount();

    @GetMapping("/BindUserCardNumber")
    BankAccount bindUserCardNumber(@RequestParam("cId") String cId);

    @PostMapping("/OpeningFundAccount")
    ResultCode openingFundAccount(
            @RequestParam("cardId") String cardId,
            @RequestParam("cId") String cId,
            @RequestParam("companyName") String companyName
    );

    @PostMapping("/ConfirmTransaction")
    ResultCode confirmTransaction(
            @RequestParam String companyName,
            @RequestParam String cId,
            @RequestParam String cardId,
            @RequestParam String fundId,
            @RequestParam double changeInShares
    );
}
