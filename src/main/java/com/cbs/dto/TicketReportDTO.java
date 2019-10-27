package com.cbs.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public interface TicketReportDTO {
	Long getOrderId();
	Long getMemberId();
	LocalDateTime getOrderTime();
	Float getAmount();
	
	
	
//	public getOrderId();  orderId;
//	private String memberId;
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	private LocalDateTime  orderTime;
//	private Double amount;
}
