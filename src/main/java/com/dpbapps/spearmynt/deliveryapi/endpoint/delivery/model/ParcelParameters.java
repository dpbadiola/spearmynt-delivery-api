package com.dpbapps.spearmynt.deliveryapi.endpoint.delivery.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class ParcelParameters {

	private BigDecimal weight;
	private BigDecimal height;
	private BigDecimal width;
	private BigDecimal length;

	public BigDecimal getVolume() {
		if (Objects.isNull(height) ||
				Objects.isNull(width) ||
				Objects.isNull(length)) {
			return BigDecimal.ZERO;
		}

		return height.multiply(width).multiply(length);
	}

}
