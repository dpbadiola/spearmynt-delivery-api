package com.example.exercise.model;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class LogicalOperationTest {

	@Test
	void test_exceeds() {
		BigDecimal keyValue = BigDecimal.valueOf(10.0D);
		BigDecimal testValue = BigDecimal.valueOf(15.0D);

		Assertions.assertTrue(LogicalOperation.EXCEEDS.test(Pair.of(keyValue, testValue)));
	}

	@Test
	void test_lessThan() {
		BigDecimal keyValue = BigDecimal.valueOf(15.0D);
		BigDecimal testValue = BigDecimal.valueOf(10.0D);

		Assertions.assertTrue(LogicalOperation.LESS_THAN.test(Pair.of(keyValue, testValue)));
	}

	@Test
	void test_equals() {
		BigDecimal keyValue = BigDecimal.valueOf(10.0D);
		BigDecimal testValue = BigDecimal.valueOf(10.0D);

		Assertions.assertTrue(LogicalOperation.EQUALS.test(Pair.of(keyValue, testValue)));
	}

}
