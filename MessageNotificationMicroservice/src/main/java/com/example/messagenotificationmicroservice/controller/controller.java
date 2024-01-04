package com.example.messagenotificationmicroservice.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.example.messagenotificationmicroservice.entites.ResultCode;
import com.example.messagenotificationmicroservice.entites.ReturnLogListInfo;
import com.example.messagenotificationmicroservice.services.EmailService;
import com.example.messagenotificationmicroservice.services.LoggerService;
import com.example.messagenotificationmicroservice.services.PublicService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/notification")
public class controller {

    @Autowired
    private EmailService emailService;
    @Autowired
    private LoggerService loggerService;
    @Autowired
    private PublicService publicService;

    @GetMapping("/Test")
    public String testConnectivity() {
        return "消息通知模块连接测试成功！";
    }

    @PostMapping("/SendEmail")
    public ResultCode sendEmail(
            @RequestParam("dstAddr") String dstAddr,
            @RequestParam("content") String content
    ) {
        try (Entry entry = SphU.entry("common")) {
            emailService.setTo(dstAddr);
            emailService.setBody(content);
            emailService.setSubject("基金业务变动提醒！");
            return emailService.sendEmail();
        } catch (Exception e) {
            ResultCode rc = new ResultCode(-11);

            // 使用ZoneId.of("Asia/Shanghai")设置时区为东八区
            ZoneId zoneId = ZoneId.of("Asia/Shanghai");
            ZonedDateTime timestampNow = ZonedDateTime.ofInstant(Instant.now(), zoneId);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            loggerService.writeLog(timestampNow.format(formatter), "MessageNotificationMicroservice", "SettingFault", "由于限流政策，本次发送邮箱请求已被降级。处理目标邮箱：" + dstAddr + "，发送目标内容：" + content);
            return rc;
        }
    }

    @PostMapping("WriteLog")
    public ResultCode writeLog(
            @RequestParam("timestamp") String timestamp,
            @RequestParam("microservicesname")String microservicesname,
            @RequestParam("filename") String filename,
            @RequestParam("content")String content
    ){
        return loggerService.writeLog(timestamp,microservicesname,filename,content);
    }

    @PostMapping("/UpdateFlowRule")
    public ResultCode updateFlowRule(@RequestParam("qps") int qps) {
        return publicService.updateFlowRule(qps);
    }

    @GetMapping("/ObtainLogList")
    public ReturnLogListInfo getLogList(){
        return publicService.getLogList();
    }

    @GetMapping("/ObtainLogFile")
    public ResponseEntity<Resource> getFile(@RequestParam String fileName) {
        try {
            Path filePath = Paths.get(".").resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", fileName);
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    //定义限流功能
    @PostConstruct
    public void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("common");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(2);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }




}