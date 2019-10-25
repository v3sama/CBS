package com.cbs.dto;

import java.util.ArrayList;
import java.util.List;

public class ReviewOrderDTO {
    List<String> ghe;
    String ngay;
    String suatchieu;
    String tenphim;
    String rap;
    Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTenphim() {
        return tenphim;
    }

    public void setTenphim(String tenphim) {
        this.tenphim = tenphim;
    }

    public String getRap() {
        return rap;
    }

    public void setRap(String rap) {
        this.rap = rap;
    }

    public List<String> getGhe() {
        return ghe;
    }

    public void setGhe(List<String> ghe) {
        this.ghe = ghe;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getSuatchieu() {
        return suatchieu;
    }

    public void setSuatchieu(String suatchieu) {
        this.suatchieu = suatchieu;
    }
}
