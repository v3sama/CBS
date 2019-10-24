package com.cbs.dto;

import java.util.ArrayList;
import java.util.List;

public class SeatRenderDTO {

    private ArrayList<String> rowMap;
    private String priceVip;
    private String priceThuong;

    public ArrayList<String> getRowMap() {
        return rowMap;
    }

    public void setRowMap(ArrayList<String> rowMap) {
        this.rowMap = rowMap;
    }


    public String getPriceVip() {
        return priceVip;
    }

    public void setPriceVip(String priceVip) {
        this.priceVip = priceVip;
    }

    public String getPriceThuong() {
        return priceThuong;
    }

    public void setPriceThuong(String priceThuong) {
        this.priceThuong = priceThuong;
    }
}
