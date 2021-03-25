package com.dpbapps.spearmynt.deliveryapi.endpoint.delivery;

import com.dpbapps.spearmynt.deliveryapi.endpoint.delivery.dto.DeliveryRequest;
import com.dpbapps.spearmynt.deliveryapi.endpoint.delivery.dto.DeliveryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("delivery")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryController {

	private final DeliveryFacade facade;

	@PostMapping
	public ResponseEntity<DeliveryResponse> computeCost(@Valid @RequestBody DeliveryRequest request) {
		DeliveryResponse response = facade.computeCost(request);
		return ResponseEntity.ok(response);
	}

}
