package com.example.cashmanagementmicroservice.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.bouncycastle.asn1.x509.Time;
import org.springframework.stereotype.Repository;

import com.example.cashmanagementmicroservice.entites.orderinfo;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.Date;

@Mapper
@Repository
public interface InfoMapper {
    @Select("select buylimit from amountlimit where fundCode =#{fundCode}")
    List<Double> getBuyLimit(@Param("fundCode") String fundCode);

    @Select("SELECT LPAD(MAX(CAST(ordernumber AS UNSIGNED)) + 1, 5, '0') AS next_order_number FROM Ordertable")
    String GetNewOrdernum();

    @Select("SELECT * FROM Ordertable WHERE ordertype = #{ordertype} and ordernumber = #{ordernumber}")
    Map<String, Object> GetOrder(String ordertype,int ordernumber);

    @Insert("insert into Ordertable  values(#{ordertype},#{ordernumber},#{TradeManid},#{Cardid},#{company},#{fundid},#{SubmissionAmount},#{Rate},#{share},#{orderstate},#{SubmissionTime},#{ConfirmTime})")
    void addorder(
            @Param("ordertype") String ordertype,
            @Param("ordernumber") String ordernumber,
            @Param("TradeManid") String TradeManid,
            @Param("Cardid") String Cardid,
            @Param("company") String company,
            @Param("fundid") String fundid,
            @Param("SubmissionAmount") String SubmissionAmount,
            @Param("Rate") Double Rate,
            @Param("share") Double share,
            @Param("orderstate") String orderstate,
            @Param("SubmissionTime") LocalDate SubmissionTime,
            @Param("ConfirmTime") LocalDate ConfirmTime);
    @Insert("insert into Cash2 values(#{accountid},#{cash})")
    void Addcash2(@Param("accountid")String accountid,@Param("cash") Double cash);

    @Update("UPDATE Ordertable SET orderstate = #{orderstate} WHERE ordernumber = #{ordernumber} and ordertype = #{ordertype}")
    void updateOrderState(@Param("ordertype") String ordertype, @Param("ordernumber") String ordernumber, @Param("orderstate") String orderstate);

    @Update("UPDATE Cash1 SET cash = cash + #{amount} WHERE accountid = #{accountid}")
    void updateCashAmountAdd(@Param("accountid") String accountid, @Param("amount") double amount);

    @Update("UPDATE Cash1 SET cash = cash - #{amount} WHERE accountid = #{accountid}")
    void updateCashAmountSubtract(@Param("accountid") String accountid, @Param("amount") double amount);

    @Insert("INSERT INTO Cash1 VALUES (#{inputAccountId}, 0)")
    void add_cash1(@Param("inputAccountId") String accountid);

    @Select("SELECT COUNT(*) AS accountExists FROM Cash1 WHERE accountid = '要检查的账户ID'")
    int ifaccout_exist(@Param("accountid") String accountid);


    @Update("UPDATE Cash2 SET cash = cash + #{amount} WHERE accountid = #{accountid}")
    void updateCashAmountAdd2(@Param("accountid") String accountid, @Param("amount") double amount);

    @Update("UPDATE Cash2 SET cash = cash - #{amount} WHERE accountid = #{accountid}")
    void updateCashAmountSubtract2(@Param("accountid") String accountid, @Param("amount") double amount);

    @Update("UPDATE Ordertable SET orderstate = 2 WHERE orderstate = 1")
    void updateOrdersToSend();

    @Update("UPDATE Ordertable SET orderstate = 3 WHERE ConfirmTime = #{confirmdate}")
    void updateOrdersToConfirm(@Param("confirmdate") String confirmdate);

    @Select("SELECT ordernumber FROM Ordertable WHERE ConfirmTime = #{datetime} AND ordertype = #{ordertype}")
    List<String> getAllOrderNumbersByDateAndType(@Param("datetime") String datetime, @Param("ordertype") String ordertype);


}
