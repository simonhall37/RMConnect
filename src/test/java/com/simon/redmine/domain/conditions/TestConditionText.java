package com.simon.redmine.domain.conditions;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

import com.simon.redmine.domain.User;

public class TestConditionText {

	@Test
	public void testCompare_shouldReturnTrue() {
		User u = new User();
		u.setName("simon.hall");
		
		ConditionText nameCheck = new ConditionText("simon.hall");
		nameCheck.setVarPath("Name");
		
		assertThat(nameCheck.compare(u)).isTrue();
		
	}
	
	@Test
	public void testCompare_shouldReturnFalse() {
		User u = new User();
		u.setName("simon.hall");
		
		ConditionText nameCheck = new ConditionText("joe.bloggs");
		nameCheck.setVarPath("Name");
		
		assertThat(nameCheck.compare(u)).isFalse();
		
	}

}
