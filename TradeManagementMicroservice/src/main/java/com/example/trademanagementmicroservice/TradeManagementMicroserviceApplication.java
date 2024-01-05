package com.example.trademanagementmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TradeManagementMicroserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeManagementMicroserviceApplication.class, args);
    }
}
