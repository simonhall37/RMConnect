package com.simon.redmine.domain.conditions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.simon.redmine.domain.User;

public class TestConditionText {

	@Test
	public void testCompare_shouldReturnTrue() {
		User u = new User();
		u.setName("simon.hall");
		
		List<String> testValue = new ArrayList<>();
		testValue.add("simon.hall");
		testValue.add("dummy");
		
		ConditionText nameCheck = new ConditionText("Name",true,testValue);
	
		assertThat(nameCheck.compare(u)).isTrue();
		
	}
	
	@Test
	public void testCompare_shouldReturnFalse() {
		User u = new User();
		u.setName("simon.hall");
		
		List<String> testValue = new ArrayList<>();
		testValue.add("empty");
		testValue.add("dummy");
		
		ConditionText nameCheck = new ConditionText("Name",true,testValue);
		
		assertThat(nameCheck.compare(u)).isFalse();
		
	}

}
