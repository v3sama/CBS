package com.cbs.dto;

import javax.persistence.Entity;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ScheduleIndexClientDTO {
    int movieId;
    String movieTitle;
    List<SessionListDTO> sessionList;



}
