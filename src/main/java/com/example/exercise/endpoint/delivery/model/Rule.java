package com.example.exercise.endpoint.delivery.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Rule {

	private long priority;
	private String name;
	private ParcelPredicate predicate;
	private LogicalOperation operation;
	private BigDecimal value;
	private ParcelPredicate multiplier;
	private BigDecimal cost;

}
