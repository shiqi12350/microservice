package com.example.cashmanagementmicroservice.services;

import com.example.cashmanagementmicroservice.entites.ResultCode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service

public class LoggerService {

    public ResultCode writeLog(String timestamp, String microservicesname, String filename, String content) {
        ResultCode rc = new ResultCode();
        String filePath = String.format("%s/%s.log", microservicesname, filename);
        Path file = Paths.get(filePath);

        try {
            Files.createDirectories(file.getParent());
            if (Files.notExists(file)) {
                Files.createFile(file);
            }

            // 使用ZoneId.of("Asia/Shanghai")设置时区为东八区
            ZoneId zoneId = ZoneId.of("Asia/Shanghai");
            ZonedDateTime timestampNow = ZonedDateTime.ofInstant(Instant.now(), zoneId);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            String logEntry = String.format("事件触发事件：%s，日志写入时间：%s，内容：%s%s",
                    timestamp, timestampNow.format(formatter), content, System.lineSeparator());
            Files.write(file, logEntry.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            rc.setResultCode(-101);
            return rc;
        }
        return rc;
    }
}
