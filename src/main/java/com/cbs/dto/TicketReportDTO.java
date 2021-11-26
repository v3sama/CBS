package com.cbs.dto;

import java.time.LocalDateTime;

import javax.servlet.Registration;

import org.springframework.format.annotation.DateTimeFormat;

public interface TicketReportDTO {
	Long getMemberId();
	//String getCustomer();
	Long getOrderId();
	LocalDateTime getOrderTime();
	String getProvince();
	String getCinema();
	String getFormat();
	String getMovie();
	LocalDateTime getSessionTime();
	Boolean getSeatType();
	Float getPrice();
}
