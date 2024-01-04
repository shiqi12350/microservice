package com.example.messagenotificationmicroservice.services;

import com.example.messagenotificationmicroservice.entites.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {
    String to;//目的邮箱
    String subject;
    String body;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    LoggerService loggerService;
    // 消息通知函数(参数：源邮箱，目的邮箱，邮件内容)



    public ResultCode sendEmail() {
        ResultCode result = new ResultCode();
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("tjtestfundsys@163.com"); // 设置发送方
            msg.setTo(this.getTo()); // 设置接收方
            msg.setSubject(this.getSubject()); // 设置邮件主题
            msg.setText(this.getBody()); // 设置邮件内容

            // 使用ZoneId.of("Asia/Shanghai")设置时区为东八区
            ZoneId zoneId = ZoneId.of("Asia/Shanghai");
            ZonedDateTime timestampNow = ZonedDateTime.ofInstant(Instant.now(), zoneId);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            loggerService.writeLog(timestampNow.format(formatter), "MessageNotificationMicroservice", "EmailSuccess", "邮件发送成功！处理目标邮箱：" + this.getTo() + "，发送目标内容：" + this.getBody());

            // 发送邮件
            javaMailSender.send(msg);
            return result;
        } catch (Exception e) {
            e.printStackTrace();

            // 使用ZoneId.of("Asia/Shanghai")设置时区为东八区
            ZoneId zoneId = ZoneId.of("Asia/Shanghai");
            ZonedDateTime timestampNow = ZonedDateTime.ofInstant(Instant.now(), zoneId);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            loggerService.writeLog(timestampNow.format(formatter), "MessageNotificationMicroservice", "EmailFault", "本次邮箱发送请求发送失败！处理目标邮箱：" + this.getTo() + "，发送目标内容：" + this.getBody() + "，报错内容：" + e.getMessage());
            result.setResultCode(-101);
            return result;
        }
    }
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}