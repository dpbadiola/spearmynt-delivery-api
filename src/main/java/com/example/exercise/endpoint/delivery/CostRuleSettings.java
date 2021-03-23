package com.example.exercise.endpoint.delivery;

import com.example.exercise.endpoint.delivery.model.Rule;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
@ConfigurationProperties(prefix = "app.delivery")
public class CostRuleSettings {

	private List<Rule> rules;

}