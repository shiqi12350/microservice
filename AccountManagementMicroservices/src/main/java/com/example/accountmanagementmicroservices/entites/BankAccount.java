package com.example.accountmanagementmicroservices.entites;

public class BankAccount {
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BankAccount(String cardId, String cId, Double amount) {
        this.cardId = cardId;
        this.cId = cId;
        this.amount = amount;
    }

    public BankAccount() {
        this.cardId = null;
        this.cId = null;
        this.amount = 0.0;
    }

    String cardId;
    String cId;
    Double amount;
}
