package com.example.exercise.endpoint.delivery;

import com.example.exercise.endpoint.delivery.model.ParcelParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CostServiceTest {

	@Autowired
	private CostService costService;

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
		Assertions.assertEquals("Reject", costService.getCondition(rejectParcel).get().getName());
	}

	@Test
	void testCondition_heavyParcel() {
		Assertions.assertEquals("Heavy Parcel", costService.getCondition(heavyParcel).get().getName());
	}

	@Test
	void testCondition_smallParcel() {
		Assertions.assertEquals("Small Parcel", costService.getCondition(smallParcel).get().getName());
	}

	@Test
	void testCondition_mediumParcel() {
		Assertions.assertEquals("Medium Parcel", costService.getCondition(mediumParcel).get().getName());
	}

	@Test
	void testCondition_largeParcel() {
		Assertions.assertEquals("Large Parcel", costService.getCondition(largeParcel).get().getName());
	}

}
