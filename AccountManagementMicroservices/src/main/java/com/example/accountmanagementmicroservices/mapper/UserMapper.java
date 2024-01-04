package com.example.accountmanagementmicroservices.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into tradeaccount values(#{cName},#{cId},#{cEmail})")
    void insertNewTradeInfo(@Param("cName")String cName,@Param("cId")String cId,@Param("cEmail")String cEmail);

    @Select("select cName from tradeaccount where cId = #{cId}")
    List<String> checkCIdExist(@Param("cId")String cId);

    @Insert("insert into bankaccount values(#{cardId},1000000,#{cId})")
    void insertNewBankAccount(@Param("cardId")String cardId,@Param("cId")String cId);

    @Select("select cEmail from tradeaccount where cId = #{cId}")
    String getEmailFromId(@Param("cId")String cId);

    @Select("select cName from tradeaccount where cId = #{cId}")
    String getNameFromId(@Param("cId")String cId);

    @Select("select cardId from fundaccount where cardId = #{cardId} and cId=#{cId} and companyName=#{companyName}")
    List<String> checkFundAccountExist(@Param("cardId")String cardId,@Param("cId")String cId,@Param("companyName")String companyName);

    @Insert("insert into fundaccount values(#{companyName},#{cId},#{cardId})")
    void insertNewFundAccount(@Param("cardId")String cardId,@Param("cId")String cId,@Param("companyName")String companyName);

    @Select("select portion from fundaccountdetail where cardId = #{cardId} and cId=#{cId} and companyName=#{companyName} and fundId = #{fundId}")
    List<Double> checkFundAccountItemExist(@Param("cardId")String cardId,@Param("cId")String cId,@Param("companyName")String companyName,@Param("fundId")String fundId);

    @Delete("delete from fundaccountdetail where cardId = #{cardId} and cId=#{cId} and companyName=#{companyName} and fundId = #{fundId}")
    void deleteFundAccountItem(@Param("cardId")String cardId,@Param("cId")String cId,@Param("companyName")String companyName,@Param("fundId")String fundId);

    @Insert("insert into fundaccountdetail values(#{companyName},#{cId},#{cardId},#{fundId},#{portion})")
    void insertFundAccountItem(@Param("cardId")String cardId,@Param("cId")String cId,@Param("companyName")String companyName,@Param("fundId")String fundId,@Param("portion")double portion);

    @Update("update fundaccountdetail set portion=#{portion} where cardId = #{cardId} and cId=#{cId} and companyName=#{companyName} and fundId = #{fundId}")
    void updateFundAccountItem(@Param("cardId")String cardId,@Param("cId")String cId,@Param("companyName")String companyName,@Param("fundId")String fundId,@Param("portion")double portion);
}
