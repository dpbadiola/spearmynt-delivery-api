package com.example.exercise.endpoint.delivery.model;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Predicate;

@Getter
public enum Condition {

	EXCEEDS(pair -> pair.getLeft() > pair.getRight()),
	LESS_THEN(pair -> pair.getLeft() < pair.getRight()),
	EQUALS(pair -> pair.getLeft() == pair.getRight()),
	NONE(pair -> true);

	Condition(Predicate<Pair<Double, Double>> predicate) {
		this.predicate = predicate;
	}

	private final Predicate<Pair<Double, Double>> predicate;

}
