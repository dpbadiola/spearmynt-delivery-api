package com.example.exercise.endpoint.delivery.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DeliveryResponse {

	private long priority;
	private String ruleName;
	private BigDecimal cost;

}
