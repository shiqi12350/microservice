package com.example.cashmanagementmicroservice.services;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.example.cashmanagementmicroservice.entites.LogFileInfo;
import com.example.cashmanagementmicroservice.entites.ResultCode;
import com.example.cashmanagementmicroservice.entites.ReturnLogListInfo;
import com.example.cashmanagementmicroservice.entites.orderinfo;
import com.example.cashmanagementmicroservice.mapper.InfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogFile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PublicService {
    @Autowired
    LoggerService loggerService;

    @Autowired
    InfoMapper infomapper;

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

            loggerService.writeLog(timestampNow.format(formatter), "MessageNotificationMicroservice", "SettingSuccess",
                    "设置限流策略成功，设置的限流数" + qps);
        } catch (Exception e) {
            // 使用ZoneId.of("Asia/Shanghai")设置时区为东八区
            ZoneId zoneId = ZoneId.of("Asia/Shanghai");
            ZonedDateTime timestampNow = ZonedDateTime.ofInstant(Instant.now(), zoneId);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            loggerService.writeLog(timestampNow.format(formatter), "MessageNotificationMicroservice", "SettingFault",
                    "设置限流策略错误，设置的限流数" + qps);
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

    public ReturnLogListInfo getLogList() {
        ReturnLogListInfo rc = new ReturnLogListInfo();

        try {
            String rootPath = "."; // 替换成实际的根路径
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

    ////// lyy///////
    public String getnewordernum() {
        String rc = infomapper.GetNewOrdernum();
        return rc;
    }

    public ResultCode add_an_order(String ordertype, String ordernumber, String TradeManid, String Cardid,
            String company, String fundid, String SubmissionAmount, Double Rate, Double share, String orderstate,
            LocalDate SubmissionTime, LocalDate ConfirmTime) {
        //int ordernumber_int = Integer.parseInt(ordernumber);
        ResultCode rc = new ResultCode(1);
        try {
            infomapper.addorder(ordertype, ordernumber, TradeManid, Cardid, company, fundid, SubmissionAmount, Rate,
                    share, orderstate, SubmissionTime, ConfirmTime);

        } catch (Exception e) {
            rc.setResultCode(-101);
            return rc;
        }

        return rc;
    }

    public Map<String, Object> Getorder(String ordertype,String ordernumber) {
        int ordernumber_int = Integer.parseInt(ordernumber);
        return infomapper.GetOrder(ordertype,ordernumber_int);
    }

    public void Changestate(String ordertype,String ordernumber, String orderstate) {
        infomapper.updateOrderState(ordertype, ordernumber, orderstate);
        // 不需要返回值，因为这个方法只是用来更新订单状态
    }


    public void Changecash1(String accountid, String SuborAdd, String cashnum) {
        double amount = Double.parseDouble(cashnum);

        if (SuborAdd.equals("Add")) {
            if(infomapper.ifaccout_exist(accountid)==0){
                infomapper.add_cash1(accountid);
            }
            infomapper.updateCashAmountAdd(accountid, amount);

        } else if (SuborAdd.equals("Sub")) {
            infomapper.updateCashAmountSubtract(accountid, amount);
        } else {
            // 如果 SuborAdd 不是 "+" 或 "-"，抛出异常或者进行其他错误处理
            throw new IllegalArgumentException("SuborAdd must be '+' or '-'");
        }
    }

    public void Changecash2(String accountid, String SuborAdd, String cashnum) {
        double amount = Double.parseDouble(cashnum);

        if (SuborAdd.equals("Add")) {
            infomapper.updateCashAmountAdd2(accountid, amount);
        } else if (SuborAdd.equals("Sub")) {
            infomapper.updateCashAmountSubtract2(accountid, amount);
        } else {
            // 如果 SuborAdd 不是 "+" 或 "-"，抛出异常或者进行其他错误处理
            throw new IllegalArgumentException("SuborAdd must be '+' or '-'");
        }
    }

     public void Dailysend() {
        infomapper.updateOrdersToSend();
    }


    public void Dailyconfirm(String confirmdate) {
        infomapper.updateOrdersToConfirm(confirmdate);
    }

    public List<String> getAllOrderNumbersByDateAndType(String datetime, String ordertype) {
        return infomapper.getAllOrderNumbersByDateAndType(datetime, ordertype);
    }

    public void Addcash2(String accountid, Double cash) {
        infomapper.Addcash2(accountid,cash);
    }
}
