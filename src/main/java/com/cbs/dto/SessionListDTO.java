package com.cbs.dto;

public class SessionListDTO {
    long id;
    String time;

    public SessionListDTO() {

    }

    public SessionListDTO(long id, String time) {
        this.id = id;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
