package com.example.informationmaintenancemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InformationMaintenanceMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InformationMaintenanceMicroserviceApplication.class, args);
    }

}
