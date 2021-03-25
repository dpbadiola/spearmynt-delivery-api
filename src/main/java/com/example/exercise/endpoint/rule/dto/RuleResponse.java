package com.example.exercise.endpoint.rule.dto;

import com.example.exercise.model.CostDefinition;
import com.example.exercise.model.Rule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleResponse {

	private Rule rule;
	private CostDefinition definition;

}
