package com.cbs.dto;

import java.util.ArrayList;

public class SeatRenderDTO {

    private ArrayList<String> rowMap;
    private float priceVip;
    private float priceThuong;

    public ArrayList<String> getRowMap() {
        return rowMap;
    }

    public void setRowMap(ArrayList<String> rowMap) {
        this.rowMap = rowMap;
    }


    public float getPriceVip() {
        return priceVip;
    }

    public void setPriceVip(float priceVip) {
        this.priceVip = priceVip;
    }

    public float getPriceThuong() {
        return priceThuong;
    }

    public void setPriceThuong(float priceThuong) {
        this.priceThuong = priceThuong;
    }
}
