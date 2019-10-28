package com.cbs.dto;

import java.time.LocalDateTime;

import javax.servlet.Registration;

import org.springframework.format.annotation.DateTimeFormat;

import com.cbs.model.Ticket;

import lombok.Getter;
import lombok.Setter;


public interface TicketReportDTO {
	
	Long getOderId();
	//Long getMemberId();

	LocalDateTime getOrderTime();
	Float getAmount();
	
//	public getOrderId();  orderId;
//	private String memberId;
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	private LocalDateTime  orderTime;
//	private Double amount;
}
