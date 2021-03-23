package com.example.exercise.endpoint.delivery.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParcelParameters {

	private double weight;
	private double volume;
	private double height;
	private double width;
	private double length;

}
