package com.example.exercise.model;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.function.Predicate;

@Getter
public enum LogicalOperation {

	EXCEEDS(pair -> pair.getLeft().compareTo(pair.getRight()) < 0),
	LESS_THAN(pair -> pair.getLeft().compareTo(pair.getRight()) > 0),
	EQUALS(pair -> pair.getLeft().compareTo(pair.getRight()) == 0),
	NONE(pair -> true);

	LogicalOperation(Predicate<Pair<BigDecimal, BigDecimal>> predicate) {
		this.predicate = predicate;
	}

	private final Predicate<Pair<BigDecimal, BigDecimal>> predicate;

	public boolean test(Pair<BigDecimal, BigDecimal> pair) {
		return predicate.test(pair);
	}

}
