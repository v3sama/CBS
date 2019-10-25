package com.cbs.dto;

import java.sql.Date;
import java.time.LocalTime;
import com.cbs.model.MovieSession;
import lombok.Data;

public interface SessionList2DTO {
	String getId();

	String getTime();
}
