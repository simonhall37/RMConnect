package com.simon.redmine.domain;

import java.util.List;

public class TimeEntryWrapper {

	private List<TimeEntry> time_entries;
	private int total_count;
	private int offset;
	private int limit;
	
	public TimeEntryWrapper() {}

	public List<TimeEntry> getTime_entries() {
		return time_entries;
	}

	public void setTime_entries(List<TimeEntry> time_entries) {
		this.time_entries = time_entries;
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
	
}
