package com.cbs.dto;

import javax.persistence.Entity;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ScheduleIndexClientDTO {
    String movie_title;
    List<SessionListDTO> sessionList;

    public List<SessionListDTO> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<SessionListDTO> sessionList) {
        this.sessionList = sessionList;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }
}
