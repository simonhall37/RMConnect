package com.simon.redmine.services;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.simon.redmine.domain.TimeEntry;
import com.simon.redmine.domain.conditions.ConditionDate;

@Service
public class TimeEntryService {

	@Autowired
	RedmineService RMService;
	@Value("${redmine.get.limit}")
	private int limit;
	@Value("${redmine.get.format}")
	private String format;
	private static final Logger log = LoggerFactory.getLogger(TimeEntryService.class);

	
	public List<TimeEntry> getEntryByDate(String startDateString, String endDateString,int maxResults){
	
		List<String> params = new ArrayList<>();
		params.add("spent_on=>=" + startDateString);
		params.add("spent_on=<=" + endDateString);
		
		ConditionDate cond = null;
				
		try{
			cond = new ConditionDate("Spent_on",true,LocalDate.parse(startDateString), LocalDate.parse(endDateString), true);
		} catch (DateTimeParseException e) {
			log.error("Could not parse the start and/or end dates " + startDateString + " - " + endDateString);
			throw new IllegalArgumentException("Could not parse the start and/or end dates " + startDateString + " - " + endDateString);
		}
		
		log.info("Querying for time entries between " + startDateString + " and " + endDateString);
		
		return RMService.getAllResponses("json", "time_entries", params,TimeEntry.class,maxResults,cond);
			
	}

}
