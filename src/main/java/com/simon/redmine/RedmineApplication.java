package com.simon.redmine;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

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
import com.simon.redmine.domain.conditions.Condition;
import com.simon.redmine.domain.conditions.ConditionDate;
import com.simon.redmine.domain.conditions.ConditionNumeric;
import com.simon.redmine.domain.conditions.ConditionText;
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
			
			String startDateString = "2018-10-15";
			String endDateString = "2018-10-19";
			
			List<TimeEntry> results = TEService.getEntryByDate(startDateString, endDateString, 50_000);
			log.info("Returned " + results.size() + " time entries");
			
			List<String> softwareTeam = new ArrayList<>();
			softwareTeam.add("Agata Nurkiewicz");
			softwareTeam.add("Lukasz Malysa");
			softwareTeam.add("Pawel Dudek");
//			softwareTeam.add("Bartek Szaflarski");
			softwareTeam.add("Szymon Pluta");
			softwareTeam.add("Bartosz Hornik");
			softwareTeam.add("Lukasz Juchnik");
			softwareTeam.add("Mariusz Markowski");
			softwareTeam.add("Adrian Lorenc");
			softwareTeam.add("Lukasz Obuchowicz");
			softwareTeam.add("Artur Poninski");
			softwareTeam.add("Denis Culavdzic");
			softwareTeam.add("Szymon Lamch");
			softwareTeam.add("Artur Rietz");
			softwareTeam.add("Michał Olszowski");
			softwareTeam.add("Krzysztof Kozubek");
			softwareTeam.add("Adam Pietras");

			List<String> consultTeam = new ArrayList<>();
			consultTeam.add("Lesli Smith");
			consultTeam.add("Geoff Brewster");
			consultTeam.add("Rob Faulkner");
			consultTeam.add("Ciaran Gilligan");
			consultTeam.add("Benn Cass");

			ConditionText nameCheck = new ConditionText(softwareTeam);
			Condition[] conds = new Condition[] {null,nameCheck,null};
			repService.setConditions(conds);
			
			for (TimeEntry te : results) {
				
				Object[] keys = new Object[3];
				Object[] values = new Object[1];
				
				LocalDate ld = LocalDate.parse((String)refService.getField(te, "Spent_on"));
				WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
				int weekNumber = ld.get(weekFields.weekOfWeekBasedYear());
				LocalDate weekStart = LocalDate.now()
			            .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekNumber)
			            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
				
				keys[0] = Integer.toString(weekNumber);
				keys[1] = refService.getField(te, "User.Name");
				keys[2] = refService.getField(te, "Project.Name");
				values[0] = refService.getField(te, "Hours");
				
				repService.setHeader("Week Number,Name,Project,Hours");
				repService.add(keys, values);
				
			}
			
			String out = repService.reduce(new ReduceOps[] {ReduceOps.SUM}, true);
			
			System.out.println(out);

		};
	}
}
