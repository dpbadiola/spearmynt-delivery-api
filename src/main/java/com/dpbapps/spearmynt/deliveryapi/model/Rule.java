package com.dpbapps.spearmynt.deliveryapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Rule {

	private Long priority;
	private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Rule rule = (Rule) o;
		return Objects.equals(priority, rule.priority) &&
				Objects.equals(name, rule.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(priority, name);
	}

}
