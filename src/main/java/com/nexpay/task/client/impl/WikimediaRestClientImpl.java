package com.nexpay.task.client.impl;

import com.jayway.jsonpath.JsonPath;
import com.nexpay.task.client.WikimediaClient;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log
@Service
public class WikimediaRestClientImpl implements WikimediaClient {

    @Value("${wiki.request.url}")
    private String requestUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public WikimediaRestClientImpl(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getListOfCountryCodesSection() {
        log.info("Requesting information of countries and codes...");
        final ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info(String.format("Request returned status: %d", response.getStatusCode().value()));
            final String body = response.getBody();
            final String jsonPath = "$.remaining.sections[10].text";
            return JsonPath.read(body, jsonPath);

        } else {
            log.severe(String.format("Error fetching a list of country codes: status %d", response.getStatusCode().value()));
            throw new IllegalStateException(String.format("Error fetching a list of country codes: status %d", response.getStatusCode().value()));
        }
    }

}
