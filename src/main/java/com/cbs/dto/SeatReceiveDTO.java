package com.cbs.dto;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class SeatReceiveDTO {
    private int sesson;
    private List<String> dataghe;
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getSesson() {
        return sesson;
    }

    public void setSesson(int sesson) {
        this.sesson = sesson;
    }

    public List<String> getDataghe() {
        return dataghe;
    }

    public void setDataghe(List<String> dataghe) {
        this.dataghe = dataghe;
    }



    @Override
    public String toString() {
        return "SeatReceiveDTO{" +
                "sesson=" + sesson +
                ", dataghe=" + dataghe +
                '}';
    }
}
