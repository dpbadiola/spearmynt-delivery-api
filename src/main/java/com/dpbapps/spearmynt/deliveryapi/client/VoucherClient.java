package com.dpbapps.spearmynt.deliveryapi.client;

import com.dpbapps.spearmynt.deliveryapi.client.dto.VoucherResponse;
import com.dpbapps.spearmynt.deliveryapi.config.VoucherApiSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VoucherClient {

	private final VoucherApiSettings apiSettings;
	private final RestTemplate restTemplate;

	public ResponseEntity<VoucherResponse> redeem(String code) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("voucherCode", code);
		parameters.put("token", apiSettings.getToken());

		return restTemplate.getForEntity(apiSettings.getUrl() + apiSettings.getVoucher(),
		                                 VoucherResponse.class,
		                                 parameters);
	}

}
