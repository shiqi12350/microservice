package com.example.accountmanagementmicroservices.entites;

public class FundAccountDetail {
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

    public String getFundId() {
        return fundId;
    }

    public void setFundId(String fundId) {
        this.fundId = fundId;
    }

    public double getPortion() {
        return portion;
    }

    public void setPortion(double portion) {
        this.portion = portion;
    }

    public FundAccountDetail(String companyName, String cId, String cardId, String fundId, double portion) {
        this.companyName = companyName;
        this.cId = cId;
        this.cardId = cardId;
        this.fundId = fundId;
        this.portion = portion;
    }

    public FundAccountDetail() {
        this.companyName = null;
        this.cId = null;
        this.cardId = null;
        this.fundId = null;
        this.portion = 0.0;
    }

    String companyName;
    String cId;
    String cardId;
    String fundId;
    double portion;
}
