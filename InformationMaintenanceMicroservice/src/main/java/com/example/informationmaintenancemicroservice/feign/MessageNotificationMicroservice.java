package com.example.informationmaintenancemicroservice.feign;

import com.example.informationmaintenancemicroservice.entites.ResultCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "MessageNotificationMicroservice", path =
        "/api/v1/notification")
public interface MessageNotificationMicroservice {
    @GetMapping("/Test")
    String testConnectivity();
    @PostMapping("/SendEmail")
    ResultCode sendEmail(@RequestParam("dstAddr") String dstAddr,
                         @RequestParam("content") String content);
    @PostMapping("/WriteLog")
    ResultCode writeLog(@RequestParam("timestamp") String timestamp,
                        @RequestParam("microservicesname") String microservicesname,
                        @RequestParam("filename") String filename,
                        @RequestParam("content") String content);
    @PostMapping("/UpdateFlowRule")
    ResultCode updateFlowRule(@RequestParam("qps") int qps);
}

