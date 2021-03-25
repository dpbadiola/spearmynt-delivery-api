package com.dpbapps.spearmynt.deliveryapi.endpoint.rule.dto;

import com.dpbapps.spearmynt.deliveryapi.model.CostDefinition;
import com.dpbapps.spearmynt.deliveryapi.model.Rule;
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
