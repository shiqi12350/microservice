package com.example.accountmanagementmicroservices.service;

import com.example.accountmanagementmicroservices.entites.BankAccount;
import com.example.accountmanagementmicroservices.entites.ResultCode;
import com.example.accountmanagementmicroservices.entites.TradeAccount;
import com.example.accountmanagementmicroservices.feign.MessageNotificationFeignServices;
import com.example.accountmanagementmicroservices.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

@Service
public class UserInfo {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MessageNotificationFeignServices messageNotificationFeignServices;

    public String getNowTime(){
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime timestampNow = ZonedDateTime.ofInstant(Instant.now(), zoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return timestampNow.format(formatter);
    }

    // 随机生成中文名
    public static String generateChineseName() {
        // 中文姓氏
        String[] surnames = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜"};
        // 中文名
        String[] givenNames = {"子璇", "淼", "国栋", "夫子", "瑞堂", "甜", "敏", "尚", "国红", "国强", "美", "洋", "欣", "雨", "月", "娜", "嘉", "雯", "馨", "涵","越野","恺","木","建国","勋","天"};

        Random random = new Random();
        String surname = surnames[random.nextInt(surnames.length)];
        String givenName = givenNames[random.nextInt(givenNames.length)];

        return surname + givenName;
    }

    // 随机生成合法的身份证号
    // 简化的地区代码数组
    private static final String[] AREA_CODE = {"110000", "120000", "130000", "140000","450201","340201"};

    // 生成随机出生日期
    private static String generateBirthDate() {
        Calendar calendar = Calendar.getInstance();
        // 随机设置出生日期范围
        int year = randBetween(1950, 2000);
        int month = randBetween(1, 12);
        int day = randBetween(1, 28); // 简化处理，没有考虑每月实际天数
        calendar.set(year, month - 1, day);
        return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
    }

    // 生成一个随机数在一个范围内
    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    // 计算校验码
    private static char calculateCheckCode(String cardId) {
        int[] coefficient = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2}; // 17位数字本体码权重
        char[] checkCode = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'}; // 校验码字符值
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (cardId.charAt(i) - '0') * coefficient[i];
        }
        return checkCode[sum % 11];
    }

    // 生成随机身份证号
    public static String generateID() {
        StringBuilder idNumber = new StringBuilder();
        // 随机选择一个地区代码
        idNumber.append(AREA_CODE[new Random().nextInt(AREA_CODE.length)]);
        // 生成随机出生日期
        idNumber.append(generateBirthDate());
        // 生成随机顺序码
        idNumber.append(String.format("%03d", randBetween(1, 999)));
        // 计算校验码
        idNumber.append(calculateCheckCode(idNumber.toString()));
        return idNumber.toString();
    }

    public TradeAccount getNewAccount(){
        try{
            TradeAccount tradeAccount = new TradeAccount(generateChineseName(),generateID(),"3613161508@qq.com");
            userMapper.insertNewTradeInfo(tradeAccount.getcName(),tradeAccount.getcId(),tradeAccount.getcEmail());
            messageNotificationFeignServices.sendEmail(tradeAccount.getcEmail(),tradeAccount.getcName()+"先生/女士，恭喜您创建账户成功！");

            messageNotificationFeignServices.writeLog(getNowTime(),"AccountManagementMicroservice","createTradeAccountSuccess", "姓名："+tradeAccount.getcName()+"，身份证号"+tradeAccount.getcId()+"，邮箱"+tradeAccount.getcEmail()+"创建成功！");
            return tradeAccount;
        }catch (Exception e){
            TradeAccount tradeAccount = new TradeAccount();
            messageNotificationFeignServices.writeLog(getNowTime(),"AccountManagementMicroservice","createTradeAccountFail", "姓名："+tradeAccount.getcName()+"，身份证号"+tradeAccount.getcId()+"，邮箱"+tradeAccount.getcEmail()+"创建失败！错误原因"+e.getMessage());
            return tradeAccount;
        }

    }

    // 生成一个19位的随机数字，第一位不是0
    public static String generateBankCardNumber() {
        Random random = new Random();
        StringBuilder numberBuilder = new StringBuilder();

        // 第一位不为0
        numberBuilder.append(random.nextInt(9) + 1);

        // 生成剩余的18位数字
        for (int i = 1; i < 19; i++) {
            numberBuilder.append(random.nextInt(10));
        }

        return numberBuilder.toString();
    }

    public BankAccount bindUserCardNumber(String cId){
        //查询这张身份证是否在系统中，如果不在系统中的话，则写入报错信息
        List<String>checknums1 =  userMapper.checkCIdExist(cId);
        if(checknums1.size()==0){
            messageNotificationFeignServices.writeLog(getNowTime(),"AccountManagementMicroservice","createBankAccountFail", "身份证号"+cId+"未存在对应的注册信息！");
            BankAccount bankAccount = new BankAccount();
            return bankAccount;
        }
        try{
            BankAccount bankAccount = new BankAccount(generateBankCardNumber(),cId,1000000.0);
            userMapper.insertNewBankAccount(bankAccount.getCardId(),bankAccount.getcId());
            messageNotificationFeignServices.sendEmail(userMapper.getEmailFromId(cId),userMapper.getNameFromId(cId)+"先生/女士，恭喜您绑定银行卡成功！您绑定的银行卡号为："+bankAccount.getCardId());

            messageNotificationFeignServices.writeLog(getNowTime(),"AccountManagementMicroservice","bindBankAccountSuccess", "姓名："+userMapper.getNameFromId(cId)+"，身份证号："+bankAccount.getcId()+"，银行卡号："+bankAccount.getCardId()+"，绑定成功！");
            return bankAccount;
        }catch (Exception e){
            BankAccount bankAccount = new BankAccount();
            messageNotificationFeignServices.writeLog(getNowTime(),"AccountManagementMicroservice","bindBankAccountSuccess", "姓名："+userMapper.getNameFromId(cId)+"，身份证号："+bankAccount.getcId()+"，银行卡号："+bankAccount.getCardId()+"，绑定失败！错误代码："+e.getMessage());
            return bankAccount;
        }
    }

    public ResultCode openingFundAccount(String cardId,String cId,String companyName){
        ResultCode rc = new ResultCode();

        try{
            //首先查询是否存在对应的账户，如果存在的话，就返回错误代码，并写入日志
            List<String>check1 = userMapper.checkFundAccountExist(cardId,cId,companyName);
            if(check1.size()!=0){
                rc.setResultCode(-1);
                messageNotificationFeignServices.writeLog(getNowTime(),"AccountManagementMicroservice","openingFundAccountFail", "此时无法创建基金账户！此时账户信息已经存在！卡号："+cardId+"，身份证号"+cId+"，基金公司名："+companyName);
                return rc;
            }

            //写入对应信息
            userMapper.insertNewFundAccount(cardId,cId,companyName);
            //写入日志
            messageNotificationFeignServices.writeLog(getNowTime(),"AccountManagementMicroservice","openingFundAccountSuccess", "此时创建基金账户成功！卡号："+cardId+"，身份证号"+cId+"，基金公司名："+companyName);
            //发送通知短信
            messageNotificationFeignServices.sendEmail(userMapper.getEmailFromId(cId),"此时创建基金账户成功！卡号："+cardId+"，身份证号"+cId+"，基金公司名："+companyName);
            return rc;
        }catch (Exception e){
            rc.setResultCode(-2);
            messageNotificationFeignServices.writeLog(getNowTime(),"AccountManagementMicroservice","openingFundAccountFail", "此时无法创建基金账户！未知错误！卡号："+cardId+"，身份证号"+cId+"，基金公司名："+companyName+"错误代码："+e.getMessage());
            return rc;
        }
    }

    public ResultCode confirmTransaction(String companyName,String cId,String cardId,String fundId,double changeInShares){
        ResultCode rc =new ResultCode();
        try{
            //首先我们需要查询是否开设了对应的基金账户
            List<String>check1 = userMapper.checkFundAccountExist(cardId,cId,companyName);
            if(check1.size()==0){
                rc.setResultCode(-1);
                messageNotificationFeignServices.writeLog(getNowTime(),"AccountManagementMicroservice","confirmTransactionFail", "此时无法创建基金信息条目！未注册对应的基金账户！卡号："+cardId+"，身份证号"+cId+"，基金公司名："+companyName+"，基金代码："+fundId+"，变更金额："+changeInShares);
                return rc;
            }

            //接下来我们需要查询是否存在对应的基金余额条目
            List<Double> check2 = userMapper.checkFundAccountItemExist(cardId,cId,companyName,fundId);

            if(check2.size()!=0){
                //如果存在的话，就正常加减
                double newValue = check2.get(0)+changeInShares;

                //如果此时计算结果为0，则删除对应的条目
                if(abs(newValue)<1e-2){
                    userMapper.deleteFundAccountItem(cardId,cId,companyName,fundId);
                }else{
                    //此时更新数据，将新数据加上
                    userMapper.updateFundAccountItem(cardId,cId,companyName,fundId,newValue);
                }


            }else{
                //如果不存在，添加条目即可
                userMapper.insertFundAccountItem(cardId,cId,companyName,fundId,changeInShares);
            }

            messageNotificationFeignServices.writeLog(getNowTime(),"AccountManagementMicroservice","confirmTransactionSuccess", "此时更新基金信息条目成功！卡号："+cardId+"，身份证号"+cId+"，基金公司名："+companyName+"，基金代码："+fundId+"，变更金额："+changeInShares);

            return rc;
        }catch (Exception e){
            rc.setResultCode(-2);
            messageNotificationFeignServices.writeLog(getNowTime(),"AccountManagementMicroservice","confirmTransactionFail", "此时无法更新基金信息条目！未知错误！卡号："+cardId+"，身份证号"+cId+"，基金公司名："+companyName+"，基金代码："+fundId+"，变更金额："+changeInShares+"错误代码："+e.getMessage());
            return rc;
        }


    }
}
