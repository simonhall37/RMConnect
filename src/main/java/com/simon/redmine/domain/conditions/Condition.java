package com.simon.redmine.domain.conditions;

import com.simon.redmine.services.ReflectionService;

public abstract class Condition {

	private String varPath;
	private boolean stopImmediately = true;
	
	public Condition() {}
	
	public Condition(String varPath, boolean stopImmediately) {
		this.varPath = varPath;
		this.stopImmediately = stopImmediately;
	}
	
	public Object getInputVar(Object parent) {
		
		ReflectionService refService = new ReflectionService();
		return refService.getField(parent, this.varPath);
		
	}

	public abstract boolean compare(Object parent);
	
	public abstract boolean compareDirect(Object toCompare);
	
	public String getVarPath() {
		return varPath;
	}

	public void setVarPath(String varPath) {
		this.varPath = varPath;
	}

	public boolean isStopImmediately() {
		return stopImmediately;
	}

	public void setStopImmediately(boolean stopImmediately) {
		this.stopImmediately = stopImmediately;
	}
	
}
