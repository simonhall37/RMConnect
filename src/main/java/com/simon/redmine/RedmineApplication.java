package com.simon.redmine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simon.redmine.domain.TimeEntry;
import com.simon.redmine.domain.User;
import com.simon.redmine.domain.conditions.ConditionDate;
import com.simon.redmine.services.RedmineService;
import com.simon.redmine.services.ReduceOps;
import com.simon.redmine.services.ReflectionService;
import com.simon.redmine.services.ReportService;
import com.simon.redmine.services.TimeEntryService;

@SpringBootApplication
public class RedmineApplication {
	
	private static final Logger log = LoggerFactory.getLogger(RedmineApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RedmineApplication.class, args);
	}
	
	@Autowired
	private TimeEntryService TEService;
	
	@Autowired
	private RedmineService RMService;
	
	@Autowired
	private ReflectionService refService;
	
	@Autowired
	private ReportService repService;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			
			String startDateString = "2018-07-27";
			String endDateString = "2018-07-27";
//			List<TimeEntry> w = TEService.getEntriesByDate(startDateString,endDateString);
			
			List<String> params = new ArrayList<>();
			params.add("spent_on=>=" + startDateString);
			params.add("spent_on=<=" + endDateString);
//			params.add("limit=" + 1);
			
			ObjectMapper om = new ObjectMapper();
			
			ConditionDate cond = new ConditionDate(LocalDate.parse("2018-07-27"), LocalDate.parse("2018-07-27"), true);
			cond.setVarPath("Spent_on");
			List<TimeEntry> test = RMService.getAllResponses("json", "time_entries", params,TimeEntry.class,900,cond);
			for (TimeEntry entry : test) {
				System.out.println(entry.getId() + " --- " + entry.getUser().getName() + " - " + entry.getHours());
			}
			
			List<User> users = RMService.getAllResponses("json", "users", new ArrayList<>(), User.class, 500,null);
			for (User u : users) {
				System.out.println(u.getId() + ": " + u.getFirstname() + " " + u.getLastname());
			}
			
			
//			ObjectMapper om = new ObjectMapper();
//			JsonNode node = om.readTree(test);
//			int count = node.get("total_count").asInt();
//			System.out.println(count);
//			JsonNode entries = node.get("payload");
//			if (entries.isArray()) {
//				for (JsonNode child : entries) {
//					TimeEntry entry = om.readValue(child.toString(), TimeEntry.class);
//					System.out.println(entry.getHours());
//				}
//			}
			
//			for (TimeEntry te : w) {
//				Object[] keys = new Object[2];
//				Object[] values = new Object[1];
//				
//				keys[0] = refService.getField(te, "User.Name");
//				keys[1] = refService.getField(te, "Project.Name");
//				values[0] = refService.getField(te, "Hours");
//				
//				repService.setHeader("Name,Project,Hours");
//				repService.add(keys, values);
//				
//			}
			
//			String out = repService.reduce(new ReduceOps[] {ReduceOps.SUM}, true);
			
//			log.info("Started application with " + w.size() + " entries");
//			log.info(out);
		};
	}
}
