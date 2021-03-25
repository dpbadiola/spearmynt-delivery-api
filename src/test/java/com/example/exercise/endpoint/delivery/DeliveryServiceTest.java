package com.example.exercise.endpoint.delivery;

import com.example.exercise.endpoint.delivery.model.ParcelParameters;
import com.example.exercise.model.CostDefinition;
import com.example.exercise.model.Rule;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DeliveryServiceTest {

	@Autowired
	private DeliveryService deliveryService;

	private ParcelParameters rejectParcel;
	private ParcelParameters heavyParcel;
	private ParcelParameters smallParcel;
	private ParcelParameters mediumParcel;
	private ParcelParameters largeParcel;

	@BeforeEach
	void setUp() {
		rejectParcel = buildParcelParameters(100.0D, 30.0D, 30.0D, 30.0D);
		heavyParcel = buildParcelParameters(15.0D, 20.0D, 20.0D, 20.0D);
		smallParcel = buildParcelParameters(10.0D, 10.0D, 10.0D, 10.0D);
		mediumParcel = buildParcelParameters(10.0D, 12.0D, 13.0D, 14.0D);
		largeParcel = buildParcelParameters(10.0D, 15.0D, 15.0D, 15.0D);
	}

	private ParcelParameters buildParcelParameters(double weight,
	                                               double height,
	                                               double width,
	                                               double length) {
		ParcelParameters parameters = new ParcelParameters();
		parameters.setWeight(BigDecimal.valueOf(weight));
		parameters.setHeight(BigDecimal.valueOf(height));
		parameters.setWidth(BigDecimal.valueOf(width));
		parameters.setLength(BigDecimal.valueOf(length));

		return parameters;
	}

	@Test
	void testCondition_reject() {
		Optional<Pair<Rule, CostDefinition>> ruleDefinition = deliveryService.getRuleDefinition(rejectParcel);
		assertTrue(ruleDefinition.isPresent());
		assertEquals("Reject", ruleDefinition.get().getKey().getName());
	}

	@Test
	void testCondition_heavyParcel() {
		Optional<Pair<Rule, CostDefinition>> ruleDefinition = deliveryService.getRuleDefinition(heavyParcel);
		assertTrue(ruleDefinition.isPresent());
		assertEquals("Heavy Parcel", ruleDefinition.get().getKey().getName());
	}

	@Test
	void testCondition_smallParcel() {
		Optional<Pair<Rule, CostDefinition>> ruleDefinition = deliveryService.getRuleDefinition(smallParcel);
		assertTrue(ruleDefinition.isPresent());
		assertEquals("Small Parcel", ruleDefinition.get().getKey().getName());
	}

	@Test
	void testCondition_mediumParcel() {
		Optional<Pair<Rule, CostDefinition>> ruleDefinition = deliveryService.getRuleDefinition(mediumParcel);
		assertTrue(ruleDefinition.isPresent());
		assertEquals("Medium Parcel", ruleDefinition.get().getKey().getName());
	}

	@Test
	void testCondition_largeParcel() {
		Optional<Pair<Rule, CostDefinition>> ruleDefinition = deliveryService.getRuleDefinition(largeParcel);
		assertTrue(ruleDefinition.isPresent());
		assertEquals("Large Parcel", ruleDefinition.get().getKey().getName());
	}

}
