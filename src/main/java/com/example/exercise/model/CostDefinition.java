package com.example.exercise.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CostDefinition {

	private ParcelPredicate condition;
	private LogicalOperation operation;
	private BigDecimal conditionValue;
	private ParcelPredicate costMultiplier;
	private BigDecimal costValue;

}
