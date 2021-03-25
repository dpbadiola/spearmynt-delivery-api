package com.example.exercise;

import com.example.exercise.config.RuleDefinitionSettings;
import com.example.exercise.model.CostDefinition;
import com.example.exercise.model.Rule;
import com.example.exercise.service.RuleDeliveryCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExerciseApplication implements CommandLineRunner {

	private final RuleDefinitionSettings ruleDefinitionSettings;
	private final RuleDeliveryCache ruleDeliveryCache;

	public ExerciseApplication(RuleDefinitionSettings ruleDefinitionSettings,
	                           RuleDeliveryCache ruleDeliveryCache) {
		this.ruleDefinitionSettings = ruleDefinitionSettings;
		this.ruleDeliveryCache = ruleDeliveryCache;
	}

	// TODO: voucher on provided swagger
	public static void main(String[] args) {
		SpringApplication.run(ExerciseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO: should be from a database
		// fill cache from rule definition from settings
		ruleDefinitionSettings.getRules().forEach(r -> {
			Rule rule = new Rule();
			rule.setName(r.getName());
			rule.setPriority(r.getPriority());

			CostDefinition definition = new CostDefinition();
			definition.setCondition(r.getPredicate());
			definition.setOperation(r.getOperation());
			definition.setConditionValue(r.getValue());
			definition.setCostMultiplier(r.getMultiplier());
			definition.setCostValue(r.getCost());

			ruleDeliveryCache.save(rule, definition);
		});
	}

}
