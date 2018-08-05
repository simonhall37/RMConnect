package com.simon.redmine.domain.conditions;

public class ConditionText extends Condition {

	private String testValue;
	
	public ConditionText(String testValue) {
		super();
		this.testValue = testValue;
	}
	
	@Override
	public boolean compare(Object parent) {
		String inputValue = (String) getInputVar(parent);
		
		return (inputValue.equalsIgnoreCase(testValue));
	}

	public String getTestValue() {
		return testValue;
	}

	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}

}
