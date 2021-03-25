package com.dpbapps.spearmynt.deliveryapi;

import com.dpbapps.spearmynt.deliveryapi.config.RuleDefinitionSettings;
import com.dpbapps.spearmynt.deliveryapi.model.CostDefinition;
import com.dpbapps.spearmynt.deliveryapi.model.Rule;
import com.dpbapps.spearmynt.deliveryapi.service.RuleDeliveryCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpearMyntDeliveryApplication implements CommandLineRunner {

	private final RuleDefinitionSettings ruleDefinitionSettings;
	private final RuleDeliveryCache ruleDeliveryCache;

	public SpearMyntDeliveryApplication(RuleDefinitionSettings ruleDefinitionSettings,
	                                    RuleDeliveryCache ruleDeliveryCache) {
		this.ruleDefinitionSettings = ruleDefinitionSettings;
		this.ruleDeliveryCache = ruleDeliveryCache;
	}

	// TODO: voucher on provided swagger
	// TODO: setup Swagger on project
	// TODO: setup health management endpoints
	public static void main(String[] args) {
		SpringApplication.run(SpearMyntDeliveryApplication.class, args);
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
