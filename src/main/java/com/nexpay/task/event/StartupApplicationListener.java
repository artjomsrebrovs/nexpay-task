package com.nexpay.task.event;

import com.nexpay.task.service.CountryCodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final CountryCodesService countryCodesService;

    @Autowired
    public StartupApplicationListener(final CountryCodesService countryCodesService) {
        this.countryCodesService = countryCodesService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        countryCodesService.populateCountryCodes();
    }
}
