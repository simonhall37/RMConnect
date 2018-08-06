package com.simon.redmine.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simon.redmine.domain.conditions.Condition;

@Service
public class RedmineService {

	@Value("${redmine.base.url}")
	private String BASE_URL;
	@Value("${redmine.apikey.name}")
	private String API_KEY_NAME;
	@Value("${redmine.apikey.environment.var}")
	private String API_ENV_VAR_NAME;
	
	private String API_KEY_VALUE;
	private final int LIMIT = 100;
	private RestTemplate restTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(RedmineService.class);

	@Autowired
	private RestTemplateBuilder builder;
	
	public void init() {
		
		if (this.restTemplate == null)
			this.restTemplate = builder.build();

		if (this.API_KEY_VALUE == null)
			this.API_KEY_VALUE = System.getenv(API_ENV_VAR_NAME);
		if (this.API_KEY_VALUE == null) 
			throw new NullPointerException("API_KEY_VALUE is not set. Expecting an env var called " + this.API_ENV_VAR_NAME);
		
	}
	
	public String buildUrl(String type, String format,List<String> params) {
		
		StringBuilder url = new StringBuilder(BASE_URL + "/" + type + "." + format + "?");
		for (String entry : params) {
			url.append(entry + "&");
		}
		url.append("limit=" + this.LIMIT);
		
		return url.toString();
	}
	
	public String getResponse(String format, String type, List<String> params) {
		
		init();

		String urlString = buildUrl(type, format, params);

		@SuppressWarnings("serial")
		HttpEntity<String> response = restTemplate.exchange(urlString, HttpMethod.GET,
				new HttpEntity<String>(new HttpHeaders() {
					{
						set(API_KEY_NAME, API_KEY_VALUE);
					}
				}), String.class);
		return response.getBody().replaceFirst(type, "payload");
		
	}
	
	public <T extends Object> List<T> getAllResponses(String format, String type, List<String> params, Class<T> objectType, int limit,Condition cond) {
		
		List<T> out = new ArrayList<>();
		int threshold = limit;
		int offset = 0;
		ObjectMapper om = new ObjectMapper();
		
		while (out.size() <= threshold) {
			
			params.add("offset=" + this.LIMIT*offset);
			String response = getResponse(format, type, params);			
			
			params = params.subList(0, params.size()-1);
			offset++;
			
			try {
				JsonNode node = om.readTree(response);
				if (out.size() == 0) 
					threshold = Math.min(node.get("total_count").asInt(),limit);
				
				log.info("Extracting up to " + threshold + " entities of type " + objectType.getName() + ". Current size is " + out.size());

				JsonNode entries = node.get("payload");
				if (entries.isArray()) {
					for (JsonNode child : entries) {
						T entity = om.readValue(child.toString(), objectType);
						out.add(entity);
						if (out.size() >= threshold) 
							return out;
						if (cond!=null) {
							if (!cond.compare(entity)) {
								return out;
							}
						}
					}
				}
			}  catch (JsonParseException | JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return out;
		
	}

}
