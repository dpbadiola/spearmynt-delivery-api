package com.example.exercise.endpoint.delivery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryRequest {

	private double weight; // kg
	private double height; // cm
	private double width; // cm
	private double length; // cm

}
