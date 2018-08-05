package com.simon.redmine.domain.conditions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Test;

import com.simon.redmine.domain.User;

public class TestConditionNumeric {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCompare_shouldReturnTrue() {
		User u = new User();
		u.setId(100);
		
		ConditionNumeric e1 = new ConditionNumeric("=", 100);
		e1.setVarPath("Id");
		assertThat(e1.compare(u)).isTrue();
		
		ConditionNumeric e2 = new ConditionNumeric(">", 9);
		e2.setVarPath("Id");
		assertThat(e2.compare(u)).isTrue();
		
		ConditionNumeric e3 = new ConditionNumeric("<", 200);
		e3.setVarPath("Id");
		assertThat(e3.compare(u)).isTrue();
	}
	
	@Test
	public void testCompare_shouldReturnFalse() {
		User u = new User();
		u.setId(100);
		
		ConditionNumeric e1 = new ConditionNumeric("=", 101);
		e1.setVarPath("Id");
		assertThat(e1.compare(u)).isFalse();
		
		ConditionNumeric e2 = new ConditionNumeric(">", 900);
		e2.setVarPath("Id");
		assertThat(e2.compare(u)).isFalse();
		
		ConditionNumeric e3 = new ConditionNumeric("<", 100);
		e3.setVarPath("Id");
		assertThat(e3.compare(u)).isFalse();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCompare_shouldThrowIllegalArg() {
		User u = new User();
		u.setId(100);
		
		ConditionNumeric e1 = new ConditionNumeric("simon", 101);
		e1.setVarPath("Id");
		assertThat(e1.compare(u)).isFalse();

	}

}
