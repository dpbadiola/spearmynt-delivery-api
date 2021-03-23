package com.example.exercise.endpoint.delivery.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rule {

	private long priority;
	private String ruleName;

	private CostCondition ruleCondition;
	private Condition rulePredicate;
	private double ruleAmount;

	private double amount;
	private CostCondition multiplier;

}
