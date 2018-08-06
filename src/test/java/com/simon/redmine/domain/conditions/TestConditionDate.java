package com.simon.redmine.domain.conditions;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Test;

import com.simon.redmine.domain.TimeEntry;

public class TestConditionDate {

	@Test
	public void testCompare_shouldReturnTrueNoEquals() {
		TimeEntry entry = new TimeEntry();
		entry.setCreated_on("2018-07-01");
		
		ConditionDate c = new ConditionDate("Created_on",true,LocalDate.parse("2018-06-01"), LocalDate.parse("2018-08-01"), false);

		assertThat(c.compare(entry)).isTrue();
		
	}
	
	@Test
	public void testCompare_shouldReturnTrueEquals() {
		TimeEntry entry = new TimeEntry();
		entry.setCreated_on("2018-07-01");
		
		ConditionDate c = new ConditionDate("Created_on",true,LocalDate.parse("2018-07-01"), LocalDate.parse("2018-08-01"), true);
	
		assertThat(c.compare(entry)).isTrue();
		
	}
	
	@Test
	public void testCompare_shouldReturnFalseNoEquals() {
		TimeEntry entry = new TimeEntry();
		entry.setCreated_on("2018-07-01");
		
		ConditionDate c = new ConditionDate("Created_on",true,LocalDate.parse("2018-07-01"), LocalDate.parse("2018-08-01"), false);

		assertThat(c.compare(entry)).isFalse();
		
	}
	
	@Test
	public void testCompare_shouldReturnFalseEquals() {
		TimeEntry entry = new TimeEntry();
		entry.setCreated_on("2018-09-01");
		
		ConditionDate c = new ConditionDate("Created_on",true,LocalDate.parse("2018-06-01"), LocalDate.parse("2018-08-01"), true);

		assertThat(c.compare(entry)).isFalse();
		
	}

}
