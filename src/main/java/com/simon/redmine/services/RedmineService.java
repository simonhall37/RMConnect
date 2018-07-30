package com.simon.redmine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RedmineService {

	@Value("${redmine.base.url}")
	private String BASE_URL;
	@Value("${redmine.apikey.name}")
	private String API_KEY_NAME;
	@Value("${redmine.apikey.value}")
	private String API_KEY_VALUE;

	private RestTemplate restTemplate;

	@Autowired
	private RestTemplateBuilder builder;

	@SuppressWarnings("serial")
	public <T extends Object> T getResponse(String format, String type, List<String> params, Class<T> objectType) {

		if (this.restTemplate == null)
			this.restTemplate = builder.build();

		StringBuilder url = new StringBuilder(BASE_URL + "/" + type + "." + format + "?");
		for (String entry : params) {
			url.append(entry + "&");
		}
		
		String urlString = url.toString();
		System.out.println(urlString);

		HttpEntity<T> response = restTemplate.exchange(urlString, HttpMethod.GET,
				new HttpEntity<T>(new HttpHeaders() {
					{
						set(API_KEY_NAME, API_KEY_VALUE);
					}
				}), objectType);
		T returnedObject = response.getBody();
		return returnedObject;
	}

}
