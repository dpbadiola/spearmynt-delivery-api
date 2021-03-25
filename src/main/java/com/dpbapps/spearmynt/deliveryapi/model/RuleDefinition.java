package com.dpbapps.spearmynt.deliveryapi.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RuleDefinition {

	private long priority;
	private String name;
	private ParcelPredicate predicate;
	private LogicalOperation operation;
	private BigDecimal value;
	private ParcelPredicate multiplier;
	private BigDecimal cost;

}
