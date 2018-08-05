package com.simon.redmine.domain.conditions;

public class ConditionNumeric extends Condition {

	private final String type;
	private final long testValue;
	
	public ConditionNumeric(String type, long testValue){
		super();
		this.type = type;
		this.testValue = testValue;
	}

	@Override
	public boolean compare(Object parent) {
		long inputValue = (Long) getInputVar(parent);
		
		if (this.type.equalsIgnoreCase("=")) {
			return (inputValue == testValue);
		} else if (this.type.equalsIgnoreCase(">")) {
			return (inputValue > testValue);
		} else if (this.type.equalsIgnoreCase("<")) {
			return (inputValue < testValue);
		} else throw new IllegalArgumentException("Unrecognised type " + this.type);
		
	}
	

	public String getType() {
		return type;
	}

	public long getTestValue() {
		return testValue;
	}
	
}
