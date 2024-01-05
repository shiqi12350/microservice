package com.example.trademanagementmicroservice.entites;

public class TradeAccount {
    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcEmail() {
        return cEmail;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public TradeAccount(String cName, String cId, String cEmail) {
        this.cName = cName;
        this.cId = cId;
        this.cEmail = cEmail;
    }

    public TradeAccount() {
        this.cName = null;
        this.cId = null;
        this.cEmail = null;
    }

    String cName;
    String cId;
    String cEmail;
}
