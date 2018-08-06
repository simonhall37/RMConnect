package com.simon.redmine.domain.conditions;

import java.util.List;

public class ConditionText extends Condition {

	private final List<String> testValue;
	
	public ConditionText(String varPath, boolean stopImmediately,List<String> testValue) {
		super(varPath,stopImmediately);
		this.testValue = testValue;
	}
	
	public ConditionText(List<String> testValue) {
		super();
		this.testValue = testValue;
	}
	
	@Override
	public boolean compare(Object parent) {
		String inputValue = (String) getInputVar(parent);
		return compare(inputValue);	
	}
	
	@Override
	public boolean compareDirect(Object toCompare) {
		return compare((String)toCompare);
	}
	
	private boolean compare(String inputValue) {
		return (testValue.contains(inputValue));
	}


	public List<String> getTestValue() {
		return testValue;
	}


	
}
