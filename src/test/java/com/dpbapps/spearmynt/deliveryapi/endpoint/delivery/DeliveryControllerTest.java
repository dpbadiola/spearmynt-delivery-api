package com.dpbapps.spearmynt.deliveryapi.endpoint.delivery;

import com.dpbapps.spearmynt.deliveryapi.client.VoucherClient;
import com.dpbapps.spearmynt.deliveryapi.client.dto.VoucherResponse;
import com.dpbapps.spearmynt.deliveryapi.endpoint.delivery.dto.DeliveryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeliveryControllerTest {

	@MockBean private VoucherClient voucherClient;
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
				.andExpect(jsonPath("$.grossCost", equalTo(0)))
				.andExpect(jsonPath("$.voucherCode", nullValue()))
				.andExpect(jsonPath("$.voucherStatus", equalTo("NONE")))
				.andExpect(jsonPath("$.voucherDiscount", equalTo(0)))
				.andExpect(jsonPath("$.netCost", equalTo(0)))
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
				.andExpect(jsonPath("$.grossCost", equalTo(300.0)))
				.andExpect(jsonPath("$.voucherCode", nullValue()))
				.andExpect(jsonPath("$.voucherStatus", equalTo("NONE")))
				.andExpect(jsonPath("$.voucherDiscount", equalTo(0)))
				.andExpect(jsonPath("$.netCost", equalTo(300.0)))
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
				.andExpect(jsonPath("$.grossCost", equalTo(30.0)))
				.andExpect(jsonPath("$.voucherCode", nullValue()))
				.andExpect(jsonPath("$.voucherStatus", equalTo("NONE")))
				.andExpect(jsonPath("$.voucherDiscount", equalTo(0)))
				.andExpect(jsonPath("$.netCost", equalTo(30.0)))
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
				.andExpect(jsonPath("$.grossCost", equalTo(87.36)))
				.andExpect(jsonPath("$.voucherCode", nullValue()))
				.andExpect(jsonPath("$.voucherStatus", equalTo("NONE")))
				.andExpect(jsonPath("$.voucherDiscount", equalTo(0)))
				.andExpect(jsonPath("$.netCost", equalTo(87.36)))
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
				.andExpect(jsonPath("$.grossCost", equalTo(168.75)))
				.andExpect(jsonPath("$.voucherCode", nullValue()))
				.andExpect(jsonPath("$.voucherStatus", equalTo("NONE")))
				.andExpect(jsonPath("$.voucherDiscount", equalTo(0)))
				.andExpect(jsonPath("$.netCost", equalTo(168.75)))
		;
	}

	@Test
	void computeCost_heavyParcel_redeemVoucher() throws Exception {
		stubVoucherSuccessResponse("TEST", BigDecimal.TEN, DateUtils.addDays(new Date(), 1));

		DeliveryRequest request = new DeliveryRequest();
		request.setWeight(15.0D);
		request.setHeight(20.0D);
		request.setWidth(20.0D);
		request.setLength(20.0D);
		request.setVoucherCode("TEST");

		RequestBuilder requestBuilder = post("/delivery")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request));

		this.mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.priority", equalTo(2)))
				.andExpect(jsonPath("$.ruleName", equalTo("Heavy Parcel")))
				.andExpect(jsonPath("$.grossCost", equalTo(300.0)))
				.andExpect(jsonPath("$.voucherCode", equalTo("TEST")))
				.andExpect(jsonPath("$.voucherStatus", equalTo("VALID")))
				.andExpect(jsonPath("$.voucherDiscount", equalTo(10)))
				.andExpect(jsonPath("$.netCost", equalTo(290.0)))
		;
	}

	@Test
	void computeCost_heavyParcel_redeemVoucher_discountGreaterThanGrossCost() throws Exception {
		stubVoucherSuccessResponse("TEST", BigDecimal.valueOf(500.0D), DateUtils.addDays(new Date(), 1));

		DeliveryRequest request = new DeliveryRequest();
		request.setWeight(15.0D);
		request.setHeight(20.0D);
		request.setWidth(20.0D);
		request.setLength(20.0D);
		request.setVoucherCode("TEST");

		RequestBuilder requestBuilder = post("/delivery")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request));

		this.mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.priority", equalTo(2)))
				.andExpect(jsonPath("$.ruleName", equalTo("Heavy Parcel")))
				.andExpect(jsonPath("$.grossCost", equalTo(300.0)))
				.andExpect(jsonPath("$.voucherCode", equalTo("TEST")))
				.andExpect(jsonPath("$.voucherStatus", equalTo("VALID")))
				.andExpect(jsonPath("$.voucherDiscount", equalTo(500.0)))
				.andExpect(jsonPath("$.netCost", equalTo(0)))
		;
	}

	@Test
	void computeCost_heavyParcel_redeemVoucher_expired() throws Exception {
		stubVoucherSuccessResponse("TEST", BigDecimal.TEN, DateUtils.addDays(new Date(), -1));

		DeliveryRequest request = new DeliveryRequest();
		request.setWeight(15.0D);
		request.setHeight(20.0D);
		request.setWidth(20.0D);
		request.setLength(20.0D);
		request.setVoucherCode("TEST");

		RequestBuilder requestBuilder = post("/delivery")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request));

		this.mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.priority", equalTo(2)))
				.andExpect(jsonPath("$.ruleName", equalTo("Heavy Parcel")))
				.andExpect(jsonPath("$.grossCost", equalTo(300.0)))
				.andExpect(jsonPath("$.voucherCode", equalTo("TEST")))
				.andExpect(jsonPath("$.voucherStatus", equalTo("EXPIRED")))
				.andExpect(jsonPath("$.voucherDiscount", equalTo(0)))
				.andExpect(jsonPath("$.netCost", equalTo(300.0)))
		;
	}

	@Test
	void computeCost_heavyParcel_redeemVoucher_invalid() throws Exception {
		stubVoucherErrorResponse("Invalid voucher code");

		DeliveryRequest request = new DeliveryRequest();
		request.setWeight(15.0D);
		request.setHeight(20.0D);
		request.setWidth(20.0D);
		request.setLength(20.0D);
		request.setVoucherCode("TEST");

		RequestBuilder requestBuilder = post("/delivery")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request));

		this.mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.priority", equalTo(2)))
				.andExpect(jsonPath("$.ruleName", equalTo("Heavy Parcel")))
				.andExpect(jsonPath("$.grossCost", equalTo(300.0)))
				.andExpect(jsonPath("$.voucherCode", equalTo("TEST")))
				.andExpect(jsonPath("$.voucherStatus", equalTo("INVALID")))
				.andExpect(jsonPath("$.voucherDiscount", equalTo(0)))
				.andExpect(jsonPath("$.netCost", equalTo(300.0)))
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

	private void stubVoucherSuccessResponse(String code, BigDecimal discount, Date expiry) {
		VoucherResponse response = mock(VoucherResponse.class);
		when(response.getCode()).thenReturn(code);
		when(response.getDiscount()).thenReturn(discount);
		when(response.getExpiry()).thenReturn(expiry);

		when(voucherClient.redeem(anyString())).thenReturn(ResponseEntity.ok(response));
	}

	private void stubVoucherErrorResponse(String error) {
		VoucherResponse response = mock(VoucherResponse.class);
		when(response.getError()).thenReturn(error);

		when(voucherClient.redeem(anyString())).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
	}

}
