package com.cbs.dto;

import java.util.List;

public class CheckoutDTO {
    int order;
    String payment;
    List<String> cardinfo;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<String> getCardinfo() {
        return cardinfo;
    }

    public void setCardinfo(List<String> cardinfo) {
        this.cardinfo = cardinfo;
    }
}
