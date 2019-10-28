package com.cbs.dto;

import java.time.LocalDateTime;

import javax.servlet.Registration;

import org.springframework.format.annotation.DateTimeFormat;

public interface TicketReportDTO {
	Long getOrderId();
	Long getMemberId();
	LocalDateTime getOrderTime();
	Float getAmount();
	
}
