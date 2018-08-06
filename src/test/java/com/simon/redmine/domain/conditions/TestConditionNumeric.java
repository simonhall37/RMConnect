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
		
		ConditionNumeric e1 = new ConditionNumeric("Id",true,"=", 100);
		assertThat(e1.compare(u)).isTrue();
		
		ConditionNumeric e2 = new ConditionNumeric("Id",true,">", 9);
		assertThat(e2.compare(u)).isTrue();
		
		ConditionNumeric e3 = new ConditionNumeric("Id",true,"<", 200);
		assertThat(e3.compare(u)).isTrue();
	}
	
	@Test
	public void testCompare_shouldReturnFalse() {
		User u = new User();
		u.setId(100);
		
		ConditionNumeric e1 = new ConditionNumeric("Id",true,"=", 101);
		assertThat(e1.compare(u)).isFalse();
		
		ConditionNumeric e2 = new ConditionNumeric("Id",true,">", 900);
		assertThat(e2.compare(u)).isFalse();
		
		ConditionNumeric e3 = new ConditionNumeric("Id",true,"<", 100);
		assertThat(e3.compare(u)).isFalse();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCompare_shouldThrowIllegalArg() {
		User u = new User();
		u.setId(100);
		
		ConditionNumeric e1 = new ConditionNumeric("Id",true,"simon", 101);
		assertThat(e1.compare(u)).isFalse();

	}

}
