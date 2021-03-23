package com.example.exercise.endpoint.delivery;

import com.example.exercise.endpoint.delivery.dto.DeliveryRequest;
import com.example.exercise.endpoint.delivery.dto.DeliveryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryController {

	private final DeliveryFacade facade;

	// TODO: unit test
	@PostMapping
	public ResponseEntity<DeliveryResponse> computeCost(@RequestBody DeliveryRequest request) {
		DeliveryResponse response = facade.computeCost(request);
		return ResponseEntity.ok(response);
	}

}
