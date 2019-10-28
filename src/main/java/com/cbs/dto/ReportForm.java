package com.cbs.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportForm {
	private Long provinceId;
	private Long cinemaId;
	private Long customerId;
	private Long movieId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fromDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate toDate;
	private Boolean showDetail;
	private Boolean showTotal;
	private Boolean showSeatType;
	
}
