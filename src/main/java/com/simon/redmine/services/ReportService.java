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
	
	public String reduce(ReduceOps[] reductions,boolean quoteHeader) {
		StringBuilder sb = new StringBuilder();
		if (quoteHeader) {
			String[] quotedHeader = this.header.split(this.DELIM);
			sb.append(arrayToQuotedString(quotedHeader) + "\n");
		}
		else sb.append(this.header + "\n");
		
		for (Entry<String,List<String[]>> e : this.results.entrySet()) {
			int index = 0;
			sb.append(e.getKey() + this.DELIM);
			for (ReduceOps r : reductions) {
				if (r.equals(ReduceOps.COUNT)) {
					sb.append("\"" + e.getValue().size() + "\"" + this.DELIM);
				} else if (r.equals(ReduceOps.SUM)) {
					Double total = 0d;
					for (String[] sa : e.getValue()) {
						try{
							total=total + Double.parseDouble(sa[index]);
						} catch (NumberFormatException ex) {
							ex.getMessage();
						}
					}
					sb.append("\"" + total + "\"" + this.DELIM);
					index++;
				}
			}
			sb.deleteCharAt(sb.length()-1).append("\n");
			
		}
		
		System.out.println(sb.toString());
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
