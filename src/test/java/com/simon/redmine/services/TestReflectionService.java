package com.simon.redmine.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.simon.redmine.domain.Project;
import com.simon.redmine.domain.TimeEntry;

public class TestReflectionService {

	@Test
	public void testGetLocalField_shouldReturnString() {
		TimeEntry parent = new TimeEntry();
		parent.setComments("This is a test");
		
		ReflectionService service =  new ReflectionService();
		Object out = null;
		try {
			out = service.getLocalField(parent, "Comments");
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		
		assertThat("This is a test").isEqualTo(out);
	}
	
	@Test
	public void testGetLocalField_shouldReturnNumber() {
		TimeEntry parent = new TimeEntry();
		parent.setHours(8);
		
		ReflectionService service =  new ReflectionService();
		Object out = null;
		try {
			out = service.getLocalField(parent, "Hours");
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		assertThat(8d).isEqualTo(out);
	}
	
	@Test(expected = NoSuchMethodException.class)
	public void testGetLocalField_shouldThrowException() throws NoSuchMethodException {
		TimeEntry parent = new TimeEntry();
		parent.setComments("This is a test");
		
		ReflectionService service =  new ReflectionService();
		try {
			@SuppressWarnings("unused")
			Object out = service.getLocalField(parent, "noExisto");
		} catch ( SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testGetField_shouldReturnSimpleValue() {
		TimeEntry parent = new TimeEntry();
		parent.setComments("This is a test");
		
		ReflectionService service =  new ReflectionService();
		Object out = null;
		try {
			out =  service.getField(parent, "Comments");
		} catch (IllegalArgumentException  e) {
			fail(e.getMessage());
		}
		
		assertThat("This is a test").isEqualTo(out);
	}
	
	@Test
	public void testGetField_shouldReturnEmbeddedValue() {
		TimeEntry parent = new TimeEntry();
		Project project = new Project();
		project.setId(1);
		project.setName("Dummy");
		parent.setProject(project);
		
		ReflectionService service =  new ReflectionService();
		Object out = null;
		try {
			out =  service.getField(parent, "Project.Name");
		} catch (IllegalArgumentException  e) {
			fail(e.getMessage());
		}
		
		assertThat("Dummy").isEqualTo(out);
	}
	
	@Test
	public void testGetField_shouldReturnNull() {
		TimeEntry parent = new TimeEntry();
		
		ReflectionService service =  new ReflectionService();
		Object out = null;
		try {
			out =  service.getField(parent, "Project.Name");
		} catch (IllegalArgumentException  e) {
			fail(e.getMessage());
		}
		
		assertThat(out).isNull();
	}
	
	@Test
	public void testGetField_shouldReturnNullSimple() {
		TimeEntry parent = new TimeEntry();
		
		ReflectionService service =  new ReflectionService();
		Object out = null;
		try {
			out =  service.getField(parent, "noExisto");
		} catch (IllegalArgumentException  e) {
			fail(e.getMessage());
		}
		
		assertThat(out).isNull();
	}

}