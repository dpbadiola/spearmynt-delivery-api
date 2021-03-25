package com.dpbapps.spearmynt.deliveryapi.endpoint.delivery.dto;

import com.dpbapps.spearmynt.deliveryapi.endpoint.delivery.model.VoucherStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DeliveryResponse {

	private long priority;
	private String ruleName;
	private BigDecimal grossCost = BigDecimal.ZERO;
	private String voucherCode;
	private VoucherStatus voucherStatus = VoucherStatus.NONE;
	private BigDecimal voucherDiscount = BigDecimal.ZERO;
	private BigDecimal netCost;

}
