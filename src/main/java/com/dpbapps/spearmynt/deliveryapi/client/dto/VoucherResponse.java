package com.dpbapps.spearmynt.deliveryapi.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
// TODO: Remove inheritance, temporary solution
public class VoucherResponse extends VoucherErrorResponse implements Serializable {

	private String code;

	private BigDecimal discount;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date expiry;

}
