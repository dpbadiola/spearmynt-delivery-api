package com.dpbapps.spearmynt.deliveryapi.model;

import com.dpbapps.spearmynt.deliveryapi.endpoint.delivery.model.ParcelParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ParcelPredicateTest {

	private ParcelParameters parameters;

	@BeforeEach
	void setUp() {
		parameters = new ParcelParameters();
		parameters.setWeight(BigDecimal.valueOf(10.0D));
		parameters.setHeight(BigDecimal.valueOf(11.0D));
		parameters.setWidth(BigDecimal.valueOf(12.0D));
		parameters.setLength(BigDecimal.valueOf(13.0D));
	}

	@Test
	void test_weight() {
		Assertions.assertEquals(BigDecimal.valueOf(10.0D), ParcelPredicate.WEIGHT.map(parameters));
	}

	@Test
	void test_height() {
		Assertions.assertEquals(BigDecimal.valueOf(11.0D), ParcelPredicate.HEIGHT.map(parameters));
	}

	@Test
	void test_width() {
		Assertions.assertEquals(BigDecimal.valueOf(12.0D), ParcelPredicate.WIDTH.map(parameters));
	}

	@Test
	void test_length() {
		Assertions.assertEquals(BigDecimal.valueOf(13.0D), ParcelPredicate.LENGTH.map(parameters));
	}

	@Test
	void test_volume() {
		Assertions.assertEquals(BigDecimal.valueOf(1716.000D).setScale(3), ParcelPredicate.VOLUME.map(parameters));
	}

	@Test
	void test_none() {
		Assertions.assertEquals(BigDecimal.ZERO, ParcelPredicate.NONE.map(parameters));
	}

}
