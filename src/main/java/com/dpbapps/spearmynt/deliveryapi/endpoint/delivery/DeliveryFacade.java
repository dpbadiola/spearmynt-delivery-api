package com.dpbapps.spearmynt.deliveryapi.endpoint.delivery;

import com.dpbapps.spearmynt.deliveryapi.client.VoucherClient;
import com.dpbapps.spearmynt.deliveryapi.client.dto.VoucherResponse;
import com.dpbapps.spearmynt.deliveryapi.endpoint.delivery.dto.DeliveryRequest;
import com.dpbapps.spearmynt.deliveryapi.endpoint.delivery.dto.DeliveryResponse;
import com.dpbapps.spearmynt.deliveryapi.endpoint.delivery.model.VoucherStatus;
import com.dpbapps.spearmynt.deliveryapi.model.Rule;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryFacade.class);

	private final VoucherClient voucherClient;
	private final DeliveryService deliveryService;

	public DeliveryResponse computeCost(DeliveryRequest request) {
		Pair<Rule, BigDecimal> computation = deliveryService.compute(BigDecimal.valueOf(request.getWeight()),
		                                                             BigDecimal.valueOf(request.getHeight()),
		                                                             BigDecimal.valueOf(request.getWidth()),
		                                                             BigDecimal.valueOf(request.getLength()));

		Pair<BigDecimal, VoucherStatus> voucher = Pair.of(BigDecimal.ZERO, VoucherStatus.NONE);
		if (StringUtils.isNotEmpty(request.getVoucherCode())) {
			voucher = redeemVoucherDiscount(request.getVoucherCode());
		}

		BigDecimal netCost = computation.getValue().subtract(voucher.getKey());
		if (BigDecimal.ZERO.compareTo(netCost) > 0) {
			netCost = BigDecimal.ZERO;
		}

		DeliveryResponse response = new DeliveryResponse();
		response.setPriority(computation.getKey().getPriority());
		response.setRuleName(computation.getKey().getName());
		response.setGrossCost(computation.getValue());
		response.setVoucherCode(request.getVoucherCode());
		response.setVoucherStatus(voucher.getValue());
		response.setVoucherDiscount(voucher.getKey());
		response.setNetCost(netCost);

		return response;
	}

	private Pair<BigDecimal, VoucherStatus> redeemVoucherDiscount(String voucherCode) {
		ResponseEntity<VoucherResponse> responseEntity = voucherClient.redeem(voucherCode);
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			LOGGER.error("Error on redeeming voucher = {}. Got an error message = {}",
			             voucherCode,
			             responseEntity.getBody().getError());
			return Pair.of(BigDecimal.ZERO, VoucherStatus.INVALID);
		}

		if (new Date().compareTo(responseEntity.getBody().getExpiry()) > 0) {
			LOGGER.warn("Voucher[code={}] being redeemed has expired", voucherCode);
			return Pair.of(BigDecimal.ZERO, VoucherStatus.EXPIRED);
		}

		return Pair.of(responseEntity.getBody().getDiscount(), VoucherStatus.VALID);
	}

}
