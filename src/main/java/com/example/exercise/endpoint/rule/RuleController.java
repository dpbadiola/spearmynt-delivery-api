package com.example.exercise.endpoint.rule;

import com.example.exercise.endpoint.rule.dto.RuleResponse;
import com.example.exercise.model.CostDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rule")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RuleController {

	private final RuleFacade facade;

	@GetMapping
	public ResponseEntity<List<RuleResponse>> retrieveAll() {
		List<RuleResponse> responses = facade.getAll();
		return ResponseEntity.ok(responses);
	}

	@PutMapping
	public ResponseEntity<RuleResponse> update(@RequestParam("priority") Long priority,
	                                           @RequestParam("name") String name,
	                                           @RequestBody CostDefinition costDefinition) {
		RuleResponse response = facade.update(priority, name, costDefinition);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

}
