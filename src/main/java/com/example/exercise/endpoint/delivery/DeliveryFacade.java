package com.example.exercise.endpoint.delivery;

import com.example.exercise.endpoint.delivery.dto.DeliveryRequest;
import com.example.exercise.endpoint.delivery.dto.DeliveryResponse;
import com.example.exercise.model.Rule;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryFacade {

	private final DeliveryService deliveryService;

	public DeliveryResponse computeCost(DeliveryRequest request) {
		Pair<Rule, BigDecimal> computation = deliveryService.compute(BigDecimal.valueOf(request.getWeight()),
		                                                             BigDecimal.valueOf(request.getHeight()),
		                                                             BigDecimal.valueOf(request.getWidth()),
		                                                             BigDecimal.valueOf(request.getLength()));

		DeliveryResponse response = new DeliveryResponse();
		response.setPriority(computation.getLeft().getPriority());
		response.setRuleName(computation.getLeft().getName());
		response.setCost(computation.getRight());

		return response;
	}

}
