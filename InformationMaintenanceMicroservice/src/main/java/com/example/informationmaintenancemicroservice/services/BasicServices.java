package com.example.informationmaintenancemicroservice.services;

import com.example.informationmaintenancemicroservice.entites.*;
import com.example.informationmaintenancemicroservice.feign.AccountFeignClient;
import com.example.informationmaintenancemicroservice.feign.MessageNotificationMicroservice;
import com.example.informationmaintenancemicroservice.mapper.InfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class BasicServices {
    @Autowired
    AccountFeignClient accountFeignClient;

    @Autowired
    MessageNotificationMicroservice messageNotificationMicroservice;

    @Autowired
    InfoMapper infoMapper;

    public String getNowTime(){
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime timestampNow = ZonedDateTime.ofInstant(Instant.now(), zoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return timestampNow.format(formatter);
    }

    public ResultCode checkWorkingHours(String dateTimeString){
        ResultCode rc = new ResultCode(-2);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date dateTime = dateFormat.parse(dateTimeString);

            // 获取日期时间对应的星期几和小时
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateTime);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            //特判2023年全体节假日
            if(year == 2023){
                if(month == 1){
                    //元旦放假
                    if(day <=2){
                        return rc;
                    }
                    //春节放假
                    if(day<=27 &&day>=21){
                        return  rc;
                    }
                    //春节调休
                    if((day ==28 || day ==29)&& hourOfDay >= 9 && hourOfDay <= 15){
                        rc.setResultCode(1);
                        return rc;
                    }
                }

                if(month ==4) {
                    //清明放假
                    if (day == 5)
                        return rc;

                    //劳动节放假
                    if(day>=29)
                        return rc;

                    //劳动节调休
                    if(day == 23 && hourOfDay >= 9 && hourOfDay <= 15){
                        rc.setResultCode(1);
                        return rc;
                    }
                }

                if(month ==5){
                    //劳动节放假
                    if(day<=3)
                        return rc;

                    //劳动节调休
                    if(day == 6 && hourOfDay >= 9 && hourOfDay <= 15){
                        rc.setResultCode(1);
                        return rc;
                    }
                }

                if(month == 6){
                    //端午节放假
                    if(day<=24&&day>=22)
                        return rc;

                    //端午节调休
                    if(day == 25 && hourOfDay >= 9 && hourOfDay <= 15){
                        rc.setResultCode(1);
                        return rc;
                    }
                }

                if(month == 9){
                    //中秋/国庆放假
                    if(day<=30&&day>=29)
                        return rc;
                }

                if(month == 10){
                    //中秋/国庆放假
                    if(day<=6)
                        return rc;

                    //中秋/国庆节调休
                    if((day == 7||day==8) && hourOfDay >= 9 && hourOfDay <= 15){
                        rc.setResultCode(1);
                        return rc;
                    }
                }
            }

            //特判2024年全体节假日
            if(year == 2024){
                if(month == 1){
                    //元旦放假
                    if(day ==1){
                        return rc;
                    }
                }
                if(month == 2){
                    //春节放假
                    if(day>=10 &&day<=17){
                        return  rc;
                    }
                    //春节调休
                    if((day ==4 || day ==18)&& hourOfDay >= 9 && hourOfDay <= 15){
                        rc.setResultCode(1);
                        return rc;
                    }
                }

                if(month ==4) {
                    //清明放假
                    if (day == 5||day ==4||day ==6)
                        return rc;

                    //清明调休
                    if((day ==7)&& hourOfDay >= 9 && hourOfDay <= 15){
                        rc.setResultCode(1);
                        return rc;
                    }

                    //劳动节调休
                    if(day == 28 && hourOfDay >= 9 && hourOfDay <= 15){
                        rc.setResultCode(1);
                        return rc;
                    }
                }

                if(month ==5){
                    //劳动节放假
                    if(day<=5)
                        return rc;

                    //劳动节调休
                    if(day == 11 && hourOfDay >= 9 && hourOfDay <= 15){
                        rc.setResultCode(1);
                        return rc;
                    }
                }

                if(month == 6){
                    //端午节放假
                    if(day==10)
                        return rc;
                }

                if(month == 9){
                    //中秋放假
                    if(day<=17&&day>=15)
                        return rc;

                    //中秋调休
                    if(day == 14 && hourOfDay >= 9 && hourOfDay <= 15){
                        rc.setResultCode(1);
                        return rc;
                    }

                    //国庆调休
                    if(day == 29 && hourOfDay >= 9 && hourOfDay <= 15){
                        rc.setResultCode(1);
                        return rc;
                    }
                }

                if(month == 10){
                    //中秋/国庆放假
                    if(day<=7)
                        return rc;

                    //中秋/国庆节调休
                    if(day == 12 && hourOfDay >= 9 && hourOfDay <= 15){
                        rc.setResultCode(1);
                        return rc;
                    }
                }
            }

            // 判断是否为工作日（星期一到星期五）且在9点到15点之间
            if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY && hourOfDay >= 9 && hourOfDay <= 15) {
                rc.setResultCode(1);
            }
            return rc;
        }catch (Exception e){
            rc.setResultCode(-1);
            messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","checkWorkingHoursFail", "输入字符串："+dateTimeString+"，报错原因："+e.getMessage());
            return rc;
        }
    }

    public ReturnRateValue getMinPurchaseAmount(String fundCode,String requestMod){
        ReturnRateValue rc = new ReturnRateValue();

        try {
            //判断此时基金代码是否存在
            List<Double> check1 = infoMapper.getBuyLimit(fundCode);
            if(check1.size()==0){
                rc.setResultCode(-1);
                messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getMinPurchaseAmountFail", "基金代码不存在！查询基金代码："+fundCode+"，查询模式："+requestMod);
                return rc;
            }

            if(requestMod.equals("commonBuy")){
                rc.setValue(check1.get(0));
                return rc;
            } else if (requestMod.equals("startPoint")) {
                rc.setValue(infoMapper.getBuyStartPoint(fundCode));
                return rc;
            } else if (requestMod.equals("additionAmount")) {
                rc.setValue(infoMapper.getAdditionbuylimit(fundCode));
                return rc;
            }else {
                rc.setResultCode(-2);
                messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getMinPurchaseAmountFail", "查询模式不存在！查询基金代码："+fundCode+"，查询模式："+requestMod);
                return rc;
            }
        }catch (Exception e){
            rc.setResultCode(-3);
            messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getMinPurchaseAmountFail", "未知错误！查询基金代码："+fundCode+"，查询模式："+requestMod+"，报错原因："+e.getMessage());
            return rc;
        }
    }

    public ReturnValue getSubscriptionFees(String fundCode, double amount){
        ReturnValue rc = new ReturnValue();

        try {
            //判断此时基金代码是否存在
            List<SubscriptionFee> check1 = infoMapper.getSubscriptionFee(fundCode);
            if(check1.size()==0){
                rc.setResultCode(-1);
                messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getSubscriptionFeesFail", "基金代码不存在！查询基金代码："+fundCode+"，查询金额："+amount);
                return rc;
            }
            for(int i = 0;i<check1.size();i++){
                if(amount<check1.get(i).getHighLine()){
                    rc.setResultCode(1);
                    rc.setValueType(check1.get(i).getValueType());
                    rc.setValue(check1.get(i).getValue());
                    return rc;
                }
            }

            rc.setResultCode(-2);
            messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getSubscriptionFeesFail", "未找到对应区间！查询基金代码："+fundCode+"，查询金额："+amount);
            return rc;
        }catch (Exception e){
            rc.setResultCode(-3);
            messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getSubscriptionFeesFail", "未知错误！查询基金代码："+fundCode+"，查询金额："+amount+"，报错原因："+e.getMessage());
            return rc;
        }
    }

    public ReturnValue getRedemptionFees(String fundCode,double day){
        ReturnValue rc = new ReturnValue();

        try {
            //判断此时基金代码是否存在
            List<RedemptionFee> check1 = infoMapper.getRedemptionFee(fundCode);
            if(check1.size()==0){
                rc.setResultCode(-1);
                messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getRedemptionFeesFail", "基金代码不存在！查询基金代码："+fundCode+"，持有日期："+day);
                return rc;
            }
            for(int i = 0;i<check1.size();i++){
                if(day<check1.get(i).getHighDay()){
                    rc.setResultCode(1);
                    rc.setValueType(0);
                    rc.setValue(check1.get(i).getValue());
                    return rc;
                }
            }

            rc.setResultCode(-2);
            messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getRedemptionFeesFail", "未找到对应区间！查询基金代码："+fundCode+"，持有日期："+day);
            return rc;
        }catch (Exception e){
            rc.setResultCode(-3);
            messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getRedemptionFeesFail", "未知错误！查询基金代码："+fundCode+"，持有日期："+day+"，错误代码："+e.getMessage());
            return rc;
        }
    }

    public ReturnValue getSaleServicesFee(String fundCode){
        ReturnValue rc = new ReturnValue();

        try {
            //判断此时基金代码是否存在
            List<Double> check1 = infoMapper.getSaleServicesFee(fundCode);
            if(check1.size()==0){
                rc.setResultCode(-1);
                messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getSaleServicesFeeFail", "基金代码不存在！查询基金代码："+fundCode);
                return rc;
            }
            rc.setValue(check1.get(0));
            rc.setValueType(0);
            rc.setResultCode(1);
            return rc;
        }catch (Exception e){
            rc.setResultCode(-2);
            messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getSaleServicesFeeFail", "未知错误！查询基金代码："+fundCode+"，错误代码："+e.getMessage());
            return rc;
        }
    }

    public ReturnValue getManageFee(String fundCode){
        ReturnValue rc = new ReturnValue();

        try {
            //判断此时基金代码是否存在
            List<Double> check1 = infoMapper.getManageFee(fundCode);
            if(check1.size()==0){
                rc.setResultCode(-1);
                messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getManageFeeFail", "基金代码不存在！查询基金代码："+fundCode);
                return rc;
            }
            rc.setValue(check1.get(0));
            rc.setValueType(0);
            rc.setResultCode(1);
            return rc;
        }catch (Exception e){
            rc.setResultCode(-2);
            messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getManageFeeFail", "未知错误！查询基金代码："+fundCode+"，错误代码："+e.getMessage());
            return rc;
        }
    }

    public ReturnValue getCustodyFee(String fundCode){
        ReturnValue rc = new ReturnValue();

        try {
            //判断此时基金代码是否存在
            List<Double> check1 = infoMapper.getCustodyFee(fundCode);
            if(check1.size()==0){
                rc.setResultCode(-1);
                messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getCustodyFeeFail", "基金代码不存在！查询基金代码："+fundCode);
                return rc;
            }
            rc.setValue(check1.get(0));
            rc.setValueType(0);
            rc.setResultCode(1);
            return rc;
        }catch (Exception e){
            rc.setResultCode(-2);
            messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getCustodyFeeFail", "未知错误！查询基金代码："+fundCode+"，错误代码："+e.getMessage());
            return rc;
        }
    }

    public ReturnValue getDayMaxBuyAmount(String fundCode){
        ReturnValue rc = new ReturnValue();

        try {
            //判断此时基金代码是否存在
            List<Double> check1 = infoMapper.getDayMaxBuyAmount(fundCode);
            if(check1.size()==0){
                rc.setResultCode(-1);
                messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getDayMaxBuyAmountFail", "基金代码不存在！查询基金代码："+fundCode);
                return rc;
            }
            rc.setValue(check1.get(0));
            rc.setValueType(1);
            rc.setResultCode(1);
            return rc;
        }catch (Exception e){
            rc.setResultCode(-2);
            messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getDayMaxBuyAmountFail", "未知错误！查询基金代码："+fundCode+"，错误代码："+e.getMessage());
            return rc;
        }
    }

    public ReturnInfo getFundInfo(String fundCode){
        ReturnInfo rc = new ReturnInfo();

        try {
            //判断此时基金代码是否存在
            List<String> check1 = infoMapper.getFundInfo(fundCode);
            if(check1.size()==0){
                rc.setResultCode(-1);
                messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getFundInfoFail", "基金代码不存在！查询基金代码："+fundCode);
                return rc;
            }
            rc.setInfo(check1.get(0));
            rc.setResultCode(1);
            return rc;
        }catch (Exception e){
            rc.setResultCode(-2);
            messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getFundInfoFail", "未知错误！查询基金代码："+fundCode+"，错误代码："+e.getMessage());
            return rc;
        }
    }

    public ReturnValue getFundValue(String fundCode){
        ReturnValue rc = new ReturnValue();

        try {
            // 使用当前时间作为种子来初始化 Random 对象
            long seed = System.currentTimeMillis();
            Random random = new Random(seed);

            // 生成一个范围在 2 到 10 之间的随机浮点数
            double randomFloat = 2 + (random.nextDouble() * 8); // 8 是范围的宽度
            rc.setValue(randomFloat);
            rc.setValueType(1);
            rc.setResultCode(1);
            return rc;
        }catch (Exception e){
            rc.setResultCode(-2);
            messageNotificationMicroservice.writeLog(getNowTime(),"InformationMaintenanceMicroservice","getFundValueFail", "未知错误！查询基金代码："+fundCode+"，错误代码："+e.getMessage());
            return rc;
        }
    }
}
