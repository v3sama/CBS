package com.cbs.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketReportDTO {
	private String orderId;
	private String memberId;
	private LocalDateTime  orderTime;
	private Double amount;
}
