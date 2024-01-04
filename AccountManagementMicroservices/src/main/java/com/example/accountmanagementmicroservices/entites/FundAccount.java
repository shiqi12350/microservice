package com.example.accountmanagementmicroservices.entites;

public class FundAccount {
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public FundAccount(String companyName, String cId, String cardId) {
        this.companyName = companyName;
        this.cId = cId;
        this.cardId = cardId;
    }

    public FundAccount() {
        this.companyName = null;
        this.cId = null;
        this.cardId = null;
    }

    String companyName;
    String cId;
    String cardId;

}
