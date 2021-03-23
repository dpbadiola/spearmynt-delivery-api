package com.example.exercise.endpoint.delivery.model;

import lombok.Getter;

import java.util.function.Function;

@Getter
public enum CostCondition {

	WEIGHT(ParcelParameters::getWidth),
	HEIGHT(ParcelParameters::getHeight),
	WIDTH(ParcelParameters::getWidth),
	LENGTH(ParcelParameters::getLength),
	VOLUME(ParcelParameters::getVolume),
	NONE(null);

	CostCondition(Function<ParcelParameters, Double> mapFunction) {
		this.mapFunction = mapFunction;
	}

	private final Function<ParcelParameters, Double> mapFunction;

}
