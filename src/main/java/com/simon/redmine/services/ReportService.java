package com.simon.redmine.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

	private String header;
	private final Map<String, List<String[]>> results;
	private String DELIM = ",";

	public ReportService() {
		this.results = new TreeMap<>();
	}

	public void add(Object[] keyArray, Object[] valuesArray) {

		String[] values = Arrays.copyOf(valuesArray, valuesArray.length,String[].class);	
		addToMap(arrayToQuotedString(keyArray), values);

	}

	public String arrayToQuotedString(Object[] input) {

		StringBuilder sb = new StringBuilder();
		for (Object o : input) {
			sb.append("\"" + o.toString() + "\"" + this.DELIM);
		}

		return sb.substring(0, sb.length() - 1);

	}

	public void addToMap(String key, String[] value) {

		if (this.results.containsKey(key)) {
			final List<String[]> toAdd = this.results.get(key);
			toAdd.add(value);
			this.results.put(key, toAdd);
		} else {
			final List<String[]> toAdd = new ArrayList<>();
			toAdd.add(value);
			this.results.put(key, toAdd);
		}

	}
	
	public String reduce(ReduceOps[] reductions) {
		StringBuilder sb = new StringBuilder(this.header);
		
		for (Entry<String,List<String[]>> e : this.results.entrySet()) {
			sb.append(e.getKey() + this.DELIM);
			if (reductions.length != e.getValue().size())
				throw new IllegalArgumentException("reduction ops size doesn't match values size - (" + reductions.length + " - " + e.getValue().toString());
			for (ReduceOps r : reductions) {
				if (r.equals(ReduceOps.COUNT)) {
					sb.append(e.getValue().size() + this.DELIM);
				}
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}

	/* getters and setters */
	public Map<String, List<String[]>> getResults() {
		return this.results;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

}
