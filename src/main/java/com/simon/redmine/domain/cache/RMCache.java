package com.simon.redmine.domain.cache;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simon.redmine.services.ReflectionService;

public class RMCache {

	private String name;
	private LocalDateTime updatedOn;
	private Map<Long,CacheEntry> content;
	
	public RMCache() {
		this.setContent(new HashMap<>());
	}
	
	public RMCache(String name) {
		this();
		this.name = name;
	}
	
	public void add(List<Object> input) {
		final ReflectionService refService = new ReflectionService();
		for (Object o : input) {
			long id = (long) refService.getField(o, "Id");
			String uo = (String) refService.getField(o, "updated_on");
			LocalDateTime updatedOn = null;
			try{
				updatedOn = LocalDateTime.parse(uo);
			} catch (DateTimeParseException e) {
				
			}
			addEntry(id, content, updatedOn);
		}
	}
	
	public void addEntry(long id, Object content, LocalDateTime uo) {
		if (this.content.containsKey(id)) {
			if (uo!=null && uo.isAfter(this.content.get(id).getUpdatedOn())) {
				this.content.put(id, new CacheEntry(id,content,uo));
			}
		}
		else this.content.put(id, new CacheEntry(id,content,uo));
		
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Map<Long,CacheEntry> getContent() {
		return content;
	}

	public void setContent(Map<Long,CacheEntry> content) {
		this.content = content;
	}

	
	
	
}
