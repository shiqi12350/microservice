package com.example.informationmaintenancemicroservice.mapper;

import com.example.informationmaintenancemicroservice.entites.RedemptionFee;
import com.example.informationmaintenancemicroservice.entites.SubscriptionFee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InfoMapper {
    @Select("select buylimit from amountlimit where fundCode =#{fundCode}")
    List<Double> getBuyLimit(@Param("fundCode")String fundCode);

    @Select("select additionbuylimit from amountlimit where fundCode =#{fundCode}")
    Double getAdditionbuylimit(@Param("fundCode")String fundCode);

    @Select("select startpoint from amountlimit where fundCode =#{fundCode}")
    double getBuyStartPoint(@Param("fundCode")String fundCode);

    @Select("select * from subscriptionfee where fundCode =#{fundCode}")
    List<SubscriptionFee> getSubscriptionFee(@Param("fundCode")String fundCode);

    @Select("select * from redemptionfee where fundCode =#{fundCode}")
    List<RedemptionFee> getRedemptionFee(@Param("fundCode")String fundCode);

    @Select("select saleServicesFee from incidentalfee where fundCode =#{fundCode}")
    List<Double> getSaleServicesFee(@Param("fundCode")String fundCode);

    @Select("select manageFee from incidentalfee where fundCode =#{fundCode}")
    List<Double> getManageFee(@Param("fundCode")String fundCode);

    @Select("select custodyFee from incidentalfee where fundCode =#{fundCode}")
    List<Double> getCustodyFee(@Param("fundCode")String fundCode);

    @Select("select dayMaxBuyAmount from incidentalfee where fundCode =#{fundCode}")
    List<Double> getDayMaxBuyAmount(@Param("fundCode")String fundCode);

    @Select("select info from ruleInfo where fundCode =#{fundCode}")
    List<String> getFundInfo(@Param("fundCode")String fundCode);
}
