package com.example.trademanagementmicroservice.services;

import com.example.trademanagementmicroservice.entites.ResultCode;
import com.example.trademanagementmicroservice.entites.TradeOrder;
import com.example.trademanagementmicroservice.feign.InformationMaintenanceMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class Trade {

    @Autowired
    InformationMaintenanceMicroservice informationMaintenanceMicroservice;
    //public TradeOrder Redemption (){
        /*1.当天为交易日开市期间

2.申请交易，交易申请提交后发消息提示

3.如果该账户是首次购买B公司的产品，那么向基金管理公司B申请开基金账户，基金账户开户成功提示

4.申请提交后，资金从账户转至平台集中保管

5.当日闭市前，可以申请撤销交易，资金原路返回，撤销成功提示

6.闭市后，平台将交易申请统一提交至公司B

7.此时若产品C交易规则为T+N，那么T日提交申请，T日之后的第N日，客户A的基金账户上出现产品C的对应份额，份额确认成功提示

8.同时(7.8同时发生)，平台统一保管的资金，扣除规定的认购费（流向平台的佣金），其余流向银行的基金托管账户
        * */
        /*LocalTime currentTime = LocalTime.now();
        ResultCode rc=new ResultCode();
        //判断是否交易时间
        if(informationMaintenanceMicroservice.checkWorkingHours(currentTime.toString())==rc){
            TradeOrder currentOrder=new TradeOrder();


        }
        //是交易时间
        //获取基金名称


    }*/
/*申购逻辑*/


}
