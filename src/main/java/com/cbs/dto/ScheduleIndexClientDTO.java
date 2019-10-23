package com.cbs.dto;

import java.sql.Date;
import java.util.ArrayList;

public class ScheduleIndexClientDTO {
    long session_id;
    Date time;
    String movie_title;

    public long getSession_id() {
        return session_id;
    }

    public void setSession_id(long session_id) {
        this.session_id = session_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }
}
