package com.example.exercise.service;

import com.example.exercise.model.CostDefinition;
import com.example.exercise.model.Rule;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RuleDeliveryCache {

	private final Cache<Rule, CostDefinition> cache;

	public RuleDeliveryCache() {
		this.cache = Caffeine.newBuilder()
				.build();
	}

	public Map<Rule, CostDefinition> fetchAll() {
		return this.cache.asMap();
	}

	public CostDefinition fetch(Rule rule) {
		return this.cache.getIfPresent(rule);
	}

	public void save(Rule rule, CostDefinition matrix) {
		this.cache.put(rule, matrix);
	}

}
