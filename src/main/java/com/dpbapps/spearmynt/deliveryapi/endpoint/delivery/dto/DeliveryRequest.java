package com.dpbapps.spearmynt.deliveryapi.endpoint.delivery.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class DeliveryRequest {

	@PositiveOrZero
	private double weight = -1.0D; // kg

	@PositiveOrZero
	private double height = -1.0D; // cm

	@PositiveOrZero
	private double width = -1.0D; // cm

	@PositiveOrZero
	private double length = -1.0D; // cm

}
