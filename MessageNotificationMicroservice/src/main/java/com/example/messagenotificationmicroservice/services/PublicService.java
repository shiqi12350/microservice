package com.example.messagenotificationmicroservice.services;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.example.messagenotificationmicroservice.entites.LogFileInfo;
import com.example.messagenotificationmicroservice.entites.ResultCode;
import com.example.messagenotificationmicroservice.entites.ReturnLogListInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublicService {
    @Autowired
    LoggerService loggerService;
    // 根据数值更新限流规则
    // 根据数值更新限流规则
    public ResultCode updateFlowRule(int qps) {
        ResultCode rc = new ResultCode(1);
        try {
            List<FlowRule> rules = new ArrayList<>();
            FlowRule rule = new FlowRule();
            rule.setResource("common");
            rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
            rule.setCount(qps);
            rules.add(rule);
            FlowRuleManager.loadRules(rules);

            // 使用ZoneId.of("Asia/Shanghai")设置时区为东八区
            ZoneId zoneId = ZoneId.of("Asia/Shanghai");
            ZonedDateTime timestampNow = ZonedDateTime.ofInstant(Instant.now(), zoneId);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            loggerService.writeLog(timestampNow.format(formatter), "MessageNotificationMicroservice", "SettingSuccess", "设置限流策略成功，设置的限流数" + qps);
        } catch (Exception e) {
            // 使用ZoneId.of("Asia/Shanghai")设置时区为东八区
            ZoneId zoneId = ZoneId.of("Asia/Shanghai");
            ZonedDateTime timestampNow = ZonedDateTime.ofInstant(Instant.now(), zoneId);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            loggerService.writeLog(timestampNow.format(formatter), "MessageNotificationMicroservice", "SettingFault", "设置限流策略错误，设置的限流数" + qps);
            rc.setResultCode(-101);
        }
        return rc;
    }
    private List<LogFileInfo> scanLogFolders(File folder) {
        List<LogFileInfo> logFiles = new ArrayList<>();
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    List<String> logFileNames = scanLogFileNames(file);
                    if (!logFileNames.isEmpty()) {
                        logFiles.add(new LogFileInfo(file.getName(), logFileNames));
                    }
                }
            }
        }
        return logFiles;
    }

    private List<String> scanLogFileNames(File folder) {
        List<String> logFileNames = new ArrayList<>();
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".log")) {
                    logFileNames.add(file.getName());
                }
            }
        }
        return logFileNames;
    }
    public ReturnLogListInfo getLogList(){
        ReturnLogListInfo rc = new ReturnLogListInfo();

        try {
            String rootPath = ".";  // 替换成实际的根路径
            File rootDir = new File(rootPath);
            if (rootDir.exists() && rootDir.isDirectory()) {
                List<LogFileInfo> logFileInfos = scanLogFolders(rootDir);
                rc.setInfo(logFileInfos);
            }
        } catch (Exception e) {
            rc.setResultCode(-101);
            return rc;
        }

        return rc;
    }

}
