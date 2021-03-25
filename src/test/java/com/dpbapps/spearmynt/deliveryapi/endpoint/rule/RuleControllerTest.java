package com.dpbapps.spearmynt.deliveryapi.endpoint.rule;

import com.dpbapps.spearmynt.deliveryapi.model.CostDefinition;
import com.dpbapps.spearmynt.deliveryapi.model.LogicalOperation;
import com.dpbapps.spearmynt.deliveryapi.model.ParcelPredicate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RuleControllerTest {

	@Autowired private ObjectMapper objectMapper;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.alwaysDo(print())
				.build();
	}

	@Test
	void retrieveAll() throws Exception {
		RequestBuilder requestBuilder = get("/rule");

		this.mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(5)))
				.andExpect(jsonPath("$[0].rule.priority", equalTo(1)))
				.andExpect(jsonPath("$[0].rule.name", equalTo("Reject")))
				.andExpect(jsonPath("$[0].definition.condition", equalTo("WEIGHT")))
				.andExpect(jsonPath("$[0].definition.operation", equalTo("EXCEEDS")))
				.andExpect(jsonPath("$[0].definition.conditionValue", equalTo(50)))
				.andExpect(jsonPath("$[0].definition.costMultiplier", equalTo("NONE")))
				.andExpect(jsonPath("$[0].definition.costValue", equalTo(0)))
		// TODO: assert the rest of the list
		;
	}

	@Test
	void update() throws Exception {
		CostDefinition request = new CostDefinition();
		request.setCondition(ParcelPredicate.WEIGHT);
		request.setOperation(LogicalOperation.EXCEEDS);
		request.setConditionValue(BigDecimal.valueOf(14.0D));
		request.setCostMultiplier(ParcelPredicate.WEIGHT);
		request.setCostValue(BigDecimal.valueOf(20.0D));

		RequestBuilder requestBuilder = put("/rule")
				.param("priority", "2")
				.param("name", "Heavy Parcel")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request));

		this.mockMvc.perform(requestBuilder)
				.andExpect(status().isAccepted())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.rule.priority", equalTo(2)))
				.andExpect(jsonPath("$.rule.name", equalTo("Heavy Parcel")))
				.andExpect(jsonPath("$.definition.condition", equalTo("WEIGHT")))
				.andExpect(jsonPath("$.definition.operation", equalTo("EXCEEDS")))
				.andExpect(jsonPath("$.definition.conditionValue", equalTo(14.0)))
				.andExpect(jsonPath("$.definition.costMultiplier", equalTo("WEIGHT")))
				.andExpect(jsonPath("$.definition.costValue", equalTo(20.0)))
		;
		// FIXME: transactional to reset update for other tests
	}

}
