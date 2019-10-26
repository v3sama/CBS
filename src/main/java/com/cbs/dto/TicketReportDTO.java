package com.cbs.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public interface TicketReportDTO {
	Long getOderId();
	Long getMemberId();
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime getOrderTime();
	Double getAmount();
	
//	public getOrderId();  orderId;
//	private String memberId;
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	private LocalDateTime  orderTime;
//	private Double amount;
}
