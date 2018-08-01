package com.simon.redmine.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

public class TestReportService {

	@Test
	public void testAdd_addSingle() {
		
		ReportService service = new ReportService();
		Object[] keyArray = new String[] {"aaa","bbb"};
		String[] valuesArray = new String[] {"ccc","ddd"};
		
		String expectedKey = "\"aaa\",\"bbb\"";
		
		assertThat(service.getResults().size()).isEqualTo(0);
		
		service.add(keyArray, valuesArray);
		
		assertThat(service.getResults().size()).isEqualTo(1);
		for (Entry<String,List<String[]>> e: service.getResults().entrySet()) {
			assertThat(e.getKey()).isEqualTo(expectedKey);
			assertThat(e.getValue().size()).isEqualTo(1);
			assertThat(e.getValue().get(0).length).isEqualTo(2);
			String[] value = e.getValue().get(0);
			assertThat(value[0]).isEqualTo(valuesArray[0]);
			assertThat(value[1]).isEqualTo(valuesArray[1]);
			
		}
		
	}
	
	@Test
	public void testAdd_addMultipleValues() {
		
		ReportService service = new ReportService();
		Object[] keyArray = new String[] {"aaa","bbb"};
		String[] valuesArray1 = new String[] {"ccc","ddd"};
		String[] valuesArray2 = new String[] {"eee","fff"};
		
		String expectedKey = "\"aaa\",\"bbb\"";

		assertThat(service.getResults().size()).isEqualTo(0);
		
		service.add(keyArray, valuesArray1);
		service.add(keyArray, valuesArray2);
		
		assertThat(service.getResults().size()).isEqualTo(1);
		for (Entry<String,List<String[]>> e: service.getResults().entrySet()) {
			assertThat(e.getKey()).isEqualTo(expectedKey);
			assertThat(e.getValue().size()).isEqualTo(2);
			assertThat(e.getValue().get(0).length).isEqualTo(2);
			String[] value1 = e.getValue().get(0);
			assertThat(value1[0]).isEqualTo(valuesArray1[0]);
			assertThat(value1[1]).isEqualTo(valuesArray1[1]);
			assertThat(e.getValue().get(0).length).isEqualTo(2);
			String[] value2 = e.getValue().get(1);
			assertThat(value2[0]).isEqualTo(valuesArray2[0]);
			assertThat(value2[1]).isEqualTo(valuesArray2[1]);
		}
		
	}
	
	@Test
	public void testAdd_addMultipleKeys() {
		
		ReportService service = new ReportService();
		Object[] keyArray1 = new String[] {"aaa","bbb"};
		Object[] valuesArray1 = new String[] {"ccc","ddd"};
		Object[] valuesArray2 = new String[] {"eee","fff"};
		Object[] keyArray2 = new String[] {"yyy","zzz"};
		Object[] valuesArray3 = new String[] {"ggg","hhh"};
		
		String expectedKey1 = "\"aaa\",\"bbb\"";
		String expectedKey2 = "\"yyy\",\"zzz\"";
		
		assertThat(service.getResults().size()).isEqualTo(0);
		
		service.add(keyArray1, valuesArray1);
		service.add(keyArray1, valuesArray2);
		service.add(keyArray2, valuesArray3);
		
		assertThat(service.getResults().size()).isEqualTo(2);
		Iterator<Entry<String,List<String[]>>> it = service.getResults().entrySet().iterator();
		
		Entry<String,List<String[]>> e = it.next();
		assertThat(e.getKey()).isEqualTo(expectedKey1);
		assertThat(e.getValue().size()).isEqualTo(2);
		assertThat(e.getValue().get(0)).isEqualTo(valuesArray1);
		assertThat(e.getValue().get(1)).isEqualTo(valuesArray2);
		
		e = it.next();
		assertThat(e.getKey()).isEqualTo(expectedKey2);
		assertThat(e.getValue().size()).isEqualTo(1);
		assertThat(e.getValue().get(0)).isEqualTo(valuesArray3);
		
		
	}

	@Test
	public void testArrayToQuotedString() {
		
		ReportService service = new ReportService();
		Object[] input = new String[] {"item1","item2"};
		String expected = "\"item1\",\"item2\"";
		String actual = service.arrayToQuotedString(input);
		assertThat(expected).isEqualTo(actual);
		
	}

	@Test
	public void testAddToMap_shouldAddNewEntry() {
		
		ReportService service = new ReportService();
		assertThat(service.getResults().size()).isEqualTo(0);
		
		service.addToMap("key", new String[] {"value"});
		
		assertThat(service.getResults().size()).isEqualTo(1);
		assertThat(service.getResults().get("key")).isNotNull();
		assertThat(service.getResults().get("key").size()).isEqualTo(1);
		assertThat(service.getResults().get("key").get(0)).isEqualTo(new String[] {"value"});
		
	}
	
	@Test
	public void testAddToMap_shouldUpdateExistingEntry() {
		
		ReportService service = new ReportService();
		assertThat(service.getResults().size()).isEqualTo(0);
		
		service.addToMap("key",  new String[] {"value1"});
		assertThat(service.getResults().size()).isEqualTo(1);
		
		service.addToMap("key",  new String[] {"value2"});
		assertThat(service.getResults().size()).isEqualTo(1);
		assertThat(service.getResults().get("key")).isNotNull();
		assertThat(service.getResults().get("key").size()).isEqualTo(2);
		assertThat(service.getResults().get("key").get(0)).isEqualTo(new String[] {"value1"});
		assertThat(service.getResults().get("key").get(1)).isEqualTo(new  String[] {"value2"});
		
	}

}
