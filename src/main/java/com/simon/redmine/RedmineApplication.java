package com.simon.redmine;

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

import com.simon.redmine.domain.TimeEntry;
import com.simon.redmine.services.TimeEntryService;

@SpringBootApplication
public class RedmineApplication {
	
	private static final Logger log = LoggerFactory.getLogger(RedmineApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RedmineApplication.class, args);
	}
	
	@Autowired
	private TimeEntryService TEService;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			
			List<TimeEntry> w = TEService.getEntriesByDate("2018-07-23","2018-07-27");
			
			log.info("Started application with " + w.size() + " entries");
		};
	}
}
