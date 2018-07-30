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
import com.simon.redmine.domain.TimeEntryWrapper;

@Service
public class TimeEntryService {

	@Autowired
	RedmineService RMService;
	@Value("${redmine.get.limit}")
	private int limit;
	@Value("${redmine.get.format}")
	private String format;
	private static final Logger log = LoggerFactory.getLogger(TimeEntryService.class);

	public List<TimeEntry> getEntriesByDate(String startDateString, String endDateString) {

		List<TimeEntry> out = new ArrayList<>();
		
		LocalDate startDate = null, endDate = null;

		try {
			startDate = parseDate(startDateString);
			endDate = parseDate(endDateString);
		} catch (IllegalArgumentException e) {
			log.warn(e.getMessage());
			return null;
		}

		List<String> params = new ArrayList<>();
		params.add("spent_on=>=" + startDateString);
		params.add("spent_on=<=" + endDateString);
		params.add("limit=" + limit);
		TimeEntryWrapper wrapper = RMService.getResponse(format, "time_entries", params, TimeEntryWrapper.class);
		
		int checkVar = this.limit;
		int offset = 0;
		while (checkVar == this.limit && offset < 1000){
			final List<TimeEntry> part = checkDates(wrapper, startDate, endDate);
			checkVar = part.size();
			out.addAll(part);
			log.info("Read " + part.size() + " elements and adding to store. " + out.size() + " entries in total");
			final List<String> tempParams = params.subList(0, 3);
			offset=offset+100;
			tempParams.add("offset=" + offset);
			wrapper = RMService.getResponse(format, "time_entries", tempParams, TimeEntryWrapper.class);
		}
		
		return out;
	}

	private List<TimeEntry> checkDates(TimeEntryWrapper wrapper, LocalDate startDate, LocalDate endDate)
			throws IllegalArgumentException {

		List<TimeEntry> out = new ArrayList<TimeEntry>();
		
		for (TimeEntry entry : wrapper.getTime_entries()) {
			try {
				LocalDate entryDate = LocalDate.parse(entry.getSpent_on());
				if ((entryDate.equals(startDate) || entryDate.equals(endDate))
						|| (entryDate.isBefore(endDate) && entryDate.isAfter(startDate))
				) {
					out.add(entry);
				}

			} catch (DateTimeParseException e) {
				log.warn("Could not parse the date: " + entry.getSpent_on());
			}
		}

		return out;
	}

	private LocalDate parseDate(String input) throws IllegalArgumentException {
		try {
			return LocalDate.parse(input);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Could not parse: " + input);
		}
	}

}
