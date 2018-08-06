package com.simon.redmine.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.simon.redmine.domain.conditions.Condition;

@Service
public class ReportService {

	private String header;
	private final Map<String, List<String[]>> results;
	private String DELIM = ",";
	private Condition[] conditions;

	public ReportService() {
		this.results = new TreeMap<>();
	}

	public void add(Object[] keyArray, Object[] valuesArray) {

		String[] values = new String[valuesArray.length];
		
		if (this.conditions!=null) {
			int index = 0;
			if (conditions.length == keyArray.length) {
				for (Condition c : this.conditions) {
					if (c!=null && !c.compareDirect(keyArray[index])) {
						return;
					}
					index++;
				}
			} else {
				System.out.println("Length mismatch when applying conditions to keys. " + conditions.length + " and " + keyArray.length);
			}
		}

		int index = 0;
		for (Object o : valuesArray) {
			if (o instanceof Integer) {
				values[index++] = ((Integer)o).toString();
			}
			else if (o instanceof String){
				values[index++] = (String)o;
			}
			else if (o instanceof Double){
				values[index++] = ((Double)o).toString();
			}
			else {
				System.out.println("Can't cast "+ o.getClass().toString());
				throw new ClassCastException();
			}
		}
		
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

	public Condition[] getConditions() {
		return conditions;
	}

	public void setConditions(Condition[] conditions) {
		this.conditions = conditions;
	}

}
