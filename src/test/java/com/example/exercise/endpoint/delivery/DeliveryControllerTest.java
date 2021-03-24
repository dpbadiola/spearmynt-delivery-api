package com.example.exercise.endpoint.delivery;

import com.example.exercise.endpoint.delivery.dto.DeliveryRequest;
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

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeliveryControllerTest {

	@Autowired private ObjectMapper objectMapper;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.alwaysDo(print())
				.build();
	}

	@Test
	void computeCost_reject() throws Exception {
		DeliveryRequest request = new DeliveryRequest();
		request.setWeight(100.0D);
		request.setHeight(30.0D);
		request.setWidth(30.0D);
		request.setLength(30.0D);

		RequestBuilder requestBuilder = post("/delivery")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request));

		this.mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.priority", equalTo(1)))
				.andExpect(jsonPath("$.ruleName", equalTo("Reject")))
				.andExpect(jsonPath("$.cost", equalTo(0)))
		;
	}

	@Test
	void computeCost_heavyParcel() throws Exception {
		DeliveryRequest request = new DeliveryRequest();
		request.setWeight(15.0D);
		request.setHeight(20.0D);
		request.setWidth(20.0D);
		request.setLength(20.0D);

		RequestBuilder requestBuilder = post("/delivery")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request));

		this.mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.priority", equalTo(2)))
				.andExpect(jsonPath("$.ruleName", equalTo("Heavy Parcel")))
				.andExpect(jsonPath("$.cost", equalTo(300.0)))
		;
	}

	@Test
	void computeCost_smallParcel() throws Exception {
		DeliveryRequest request = new DeliveryRequest();
		request.setWeight(10.0D);
		request.setHeight(10.0D);
		request.setWidth(10.0D);
		request.setLength(10.0D);

		RequestBuilder requestBuilder = post("/delivery")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request));

		this.mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.priority", equalTo(3)))
				.andExpect(jsonPath("$.ruleName", equalTo("Small Parcel")))
				.andExpect(jsonPath("$.cost", equalTo(30.0)))
		;
	}

	@Test
	void computeCost_mediumParcel() throws Exception {
		DeliveryRequest request = new DeliveryRequest();
		request.setWeight(10.0D);
		request.setHeight(12.0D);
		request.setWidth(13.0D);
		request.setLength(14.0D);

		RequestBuilder requestBuilder = post("/delivery")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request));

		this.mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.priority", equalTo(4)))
				.andExpect(jsonPath("$.ruleName", equalTo("Medium Parcel")))
				.andExpect(jsonPath("$.cost", equalTo(87.36)))
		;
	}

	@Test
	void computeCost_largeParcel() throws Exception {
		DeliveryRequest request = new DeliveryRequest();
		request.setWeight(10.0D);
		request.setHeight(15.0D);
		request.setWidth(15.0D);
		request.setLength(15.0D);

		RequestBuilder requestBuilder = post("/delivery")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request));

		this.mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.priority", equalTo(5)))
				.andExpect(jsonPath("$.ruleName", equalTo("Large Parcel")))
				.andExpect(jsonPath("$.cost", equalTo(168.75)))
		;
	}

	@Test
	void computeCost_null() throws Exception {
		DeliveryRequest request = new DeliveryRequest();

		RequestBuilder requestBuilder = post("/delivery")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request));

		this.mockMvc.perform(requestBuilder)
				.andExpect(status().isBadRequest())
		;
	}

	@Test
	void computeCost_negativeInput() throws Exception {
		DeliveryRequest request = new DeliveryRequest();
		request.setWeight(-10.0D);
		request.setHeight(-15.0D);
		request.setWidth(-15.0D);
		request.setLength(-15.0D);

		RequestBuilder requestBuilder = post("/delivery")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request));

		this.mockMvc.perform(requestBuilder)
				.andExpect(status().isBadRequest())
		;
	}

}
