package com.dpbapps.spearmynt.deliveryapi.config;

import com.dpbapps.spearmynt.deliveryapi.model.RuleDefinition;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.delivery")
public class RuleDefinitionSettings {

	private List<RuleDefinition> rules;

}
