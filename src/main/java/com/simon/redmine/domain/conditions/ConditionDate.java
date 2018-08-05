package com.simon.redmine.domain.conditions;

import java.time.LocalDate;

public class ConditionDate extends Condition {

	private final LocalDate startDate;
	private final LocalDate endDate;
	private final boolean equalsAllowed;
	
	public ConditionDate(LocalDate startDate, LocalDate endDate, boolean equalsAllowed) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.equalsAllowed = equalsAllowed;
	}
	
	@Override
	public boolean compare(Object parent) {
		LocalDate inputValue=LocalDate.parse((String)getInputVar(parent));
		
		if (equalsAllowed) {
			return (inputValue.isEqual(startDate) || inputValue.isEqual(endDate) || (inputValue.isAfter(startDate) && inputValue.isBefore(endDate)));
		}
		else return (inputValue.isAfter(startDate) && inputValue.isBefore(endDate));
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public boolean isEqualsAllowed() {
		return equalsAllowed;
	}

}
