package com.simon.redmine.domain.conditions;

import com.simon.redmine.services.ReflectionService;

public abstract class Condition {

	private String varPath;
	
	public Condition() {}
	
	public Object getInputVar(Object parent) {
		
		ReflectionService refService = new ReflectionService();
		return refService.getField(parent, this.varPath);
		
	}

	public abstract boolean compare(Object parent);
	
	public String getVarPath() {
		return varPath;
	}

	public void setVarPath(String varPath) {
		this.varPath = varPath;
	}
	
}
