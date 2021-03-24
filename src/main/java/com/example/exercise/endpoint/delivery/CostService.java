package com.example.exercise.endpoint.delivery;

import com.example.exercise.endpoint.delivery.model.ParcelParameters;
import com.example.exercise.endpoint.delivery.model.Rule;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CostService {

	private final CostRuleSettings settings;

	public Pair<Rule, BigDecimal> compute(BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length) {
		ParcelParameters parameters = new ParcelParameters();
		parameters.setWeight(weight);
		parameters.setHeight(height);
		parameters.setWidth(width);
		parameters.setLength(length);

		Optional<Rule> rule = getCondition(parameters);
		BigDecimal amount = rule.map(r -> computeDeliveryCost(r, parameters)).orElse(BigDecimal.ZERO);

		return Pair.of(rule.orElse(null), amount);
	}

	protected Optional<Rule> getCondition(ParcelParameters parameters) {
		return settings.getRules().stream()
				.filter(rule -> filterCondition(rule, parameters))
				.findFirst();
	}

	private boolean filterCondition(Rule rule, ParcelParameters parameters) {
		BigDecimal keyValue = rule.getValue();
		BigDecimal testValue = rule.getPredicate().getMapFunction().apply(parameters);
		return rule.getOperation().test(Pair.of(keyValue, testValue));
	}

	private BigDecimal computeDeliveryCost(Rule rule, ParcelParameters parameters) {
		BigDecimal multiplier = rule.getMultiplier().map(parameters);
		BigDecimal baseAmount = rule.getCost();
		return multiplier.multiply(baseAmount);
	}

}
