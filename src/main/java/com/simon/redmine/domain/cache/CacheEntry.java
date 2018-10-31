package com.simon.redmine.domain.cache;

import java.time.LocalDateTime;

public class CacheEntry {

	private long id;
	private LocalDateTime updatedOn;
	private Object content;
	
	public CacheEntry() {}

	public CacheEntry(long id,Object content, LocalDateTime updatedOn) {
		this.id = id;
		this.content = content;
		this.updatedOn = updatedOn;
	}
	
	/* getters and setters */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
	
	
}
