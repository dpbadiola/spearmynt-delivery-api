package com.example.exercise.endpoint.delivery;

import com.example.exercise.endpoint.delivery.model.ParcelParameters;
import com.example.exercise.model.CostDefinition;
import com.example.exercise.model.Rule;
import com.example.exercise.service.RuleDeliveryCache;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryService {

	private final RuleDeliveryCache ruleDeliveryCache;

	public Pair<Rule, BigDecimal> compute(BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length) {
		ParcelParameters parameters = new ParcelParameters();
		parameters.setWeight(weight);
		parameters.setHeight(height);
		parameters.setWidth(width);
		parameters.setLength(length);

		Optional<Pair<Rule, CostDefinition>> ruleDefinition = getRuleDefinition(parameters);
		BigDecimal deliveryCost = ruleDefinition.map(entry -> computeDeliveryCost(entry.getValue(), parameters))
				.orElse(BigDecimal.ZERO);

		return Pair.of(ruleDefinition.map(Pair::getKey).orElse(null), deliveryCost);
	}

	protected Optional<Pair<Rule, CostDefinition>> getRuleDefinition(ParcelParameters parameters) {
		return ruleDeliveryCache.fetchAll().entrySet().stream()
				.sorted(Map.Entry.comparingByKey(Comparator.comparing(Rule::getPriority))) // FIXME: through-put issue!
				.filter(entry -> filterCondition(entry.getValue(), parameters))
				.map(entry -> Pair.of(entry.getKey(), entry.getValue()))
				.findFirst();
	}

	private boolean filterCondition(CostDefinition costDefinition, ParcelParameters parameters) {
		BigDecimal keyValue = costDefinition.getConditionValue();
		BigDecimal testValue = costDefinition.getCondition().map(parameters);
		return costDefinition.getOperation().test(Pair.of(keyValue, testValue));
	}

	private BigDecimal computeDeliveryCost(CostDefinition costDefinition, ParcelParameters parameters) {
		BigDecimal multiplier = costDefinition.getCostMultiplier().map(parameters);
		BigDecimal baseAmount = costDefinition.getCostValue();
		return multiplier.multiply(baseAmount);
	}

}
