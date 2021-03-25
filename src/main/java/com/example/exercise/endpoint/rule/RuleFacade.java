package com.example.exercise.endpoint.rule;

import com.example.exercise.endpoint.rule.dto.RuleResponse;
import com.example.exercise.model.CostDefinition;
import com.example.exercise.model.Rule;
import com.example.exercise.service.RuleDeliveryCache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RuleFacade {

	private final RuleDeliveryCache cache;

	public List<RuleResponse> getAll() {
		return cache.fetchAll().entrySet().stream()
				.map(entry -> new RuleResponse(entry.getKey(), entry.getValue()))
				.sorted(Comparator.comparing(r -> r.getRule().getPriority()))
				.collect(Collectors.toList());
	}

	public RuleResponse update(Long priority, String name, CostDefinition costDefinition) {
		Rule rule = new Rule();
		rule.setPriority(priority);
		rule.setName(name);

		boolean exists = cache.fetch(rule) != null;
		if (!exists) {
			throw new IllegalArgumentException("Rule not found!");
		}

		cache.save(rule, costDefinition);

		return new RuleResponse(rule, costDefinition);
	}

}
