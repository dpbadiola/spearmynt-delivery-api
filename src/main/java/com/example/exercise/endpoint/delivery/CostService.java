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

	// TODO: JavaDoc
	// TODO: Unit test
	public Pair<Rule, BigDecimal> compute(double weight, double height, double width, double length) {
		double volume = height * width * length;

		ParcelParameters parameters = new ParcelParameters();
		parameters.setWeight(weight);
		parameters.setVolume(volume);
		parameters.setHeight(height);
		parameters.setWidth(width);
		parameters.setLength(length);

		Optional<Rule> rule = getCondition(parameters);
		BigDecimal amount = rule.map(r -> computeDeliveryCost(r, parameters)).orElse(BigDecimal.ZERO);

		return Pair.of(rule.orElse(null), amount);
	}

	// TODO: JavaDoc
	// TODO: Unit test
	private Optional<Rule> getCondition(ParcelParameters parameters) {
		return settings.getRules().stream()
				.filter(rule -> {
					double keyValue = rule.getRuleAmount();
					double testValue = rule.getRuleCondition().getMapFunction().apply(parameters);
					return rule.getRulePredicate().getPredicate().test(Pair.of(keyValue, testValue));
				})
				.findFirst();
	}

	// TODO: JavaDoc
	// TODO: Unit test
	private BigDecimal computeDeliveryCost(Rule rule, ParcelParameters parameters) {
		double multiplier = rule.getMultiplier().getMapFunction().apply(parameters);
		double baseAmount = rule.getAmount();
		return BigDecimal.valueOf(multiplier * baseAmount);
	}

}
