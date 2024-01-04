package com.example.accountmanagementmicroservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AccountManagementMicroservicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountManagementMicroservicesApplication.class, args);
    }

}
