package com.example.exercise.endpoint.delivery.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.function.Function;

@Getter
public enum ParcelPredicate {

	WEIGHT(ParcelParameters::getWeight),
	HEIGHT(ParcelParameters::getHeight),
	WIDTH(ParcelParameters::getWidth),
	LENGTH(ParcelParameters::getLength),
	VOLUME(ParcelParameters::getVolume),
	NONE(obj -> BigDecimal.ZERO);

	ParcelPredicate(Function<ParcelParameters, BigDecimal> mapFunction) {
		this.mapFunction = mapFunction;
	}

	private final Function<ParcelParameters, BigDecimal> mapFunction;

	public BigDecimal map(ParcelParameters parameters) {
		return mapFunction.apply(parameters);
	}

}
