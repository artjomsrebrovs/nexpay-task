package com.nexpay.task.service.impl;

import com.nexpay.task.client.WikimediaClient;
import com.nexpay.task.cache.CountriesCache;
import com.nexpay.task.entity.Country;
import com.nexpay.task.service.CountryCodesService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log
@Service
public class CountryCodesServiceImpl implements CountryCodesService {

    private final WikimediaClient wikimediaClient;
    private final CountriesCache countriesCache;

    @Autowired
    public CountryCodesServiceImpl(final WikimediaClient wikimediaClient, final CountriesCache countriesCache) {
        this.wikimediaClient = wikimediaClient;
        this.countriesCache = countriesCache;
    }

    @Override
    public void populateCountryCodes() {
        final String listOfCountryCodesSection = wikimediaClient.getListOfCountryCodesSection();
        final List<Country> countries = parseCountriesSection(listOfCountryCodesSection);
        populateCountriesCache(countries);
    }

    private List<Country> parseCountriesSection(final String listOfCountryCodesSection) {
        log.info("Parsing and caching countries and codes...");
        final List<String> countryLines = extractCountryLines(listOfCountryCodesSection);
        final List<Country> countries = parseCountryLines(countryLines);
        return Collections.unmodifiableList(countries);
    }

    private List<String> extractCountryLines(final String listOfCountryCodesSection) {
        final Pattern pattern = Pattern.compile("<span>\\p{L}+</span></td><td align=\"right\"><a href=\"/wiki/\\+\\d+_?\\d+", Pattern.UNICODE_CHARACTER_CLASS);
        final String text = listOfCountryCodesSection.replace("\n", "").replace("\r", "");
        final Matcher matcher = pattern.matcher(text);
        return matcher.results().map(MatchResult::group).collect(Collectors.toList());
    }

    private List<Country> parseCountryLines(final List<String> countryLines) {
        final List<Country> countries = new ArrayList<>();
        for (final String countryLine : countryLines) {
            final String country = countryLine.substring(countryLine.indexOf("<span>") + 6, countryLine.indexOf("</span>"));
            final String code = countryLine.substring(countryLine.indexOf("+") + 1);
            countries.add(new Country(country, code));
        }

        return countries;
    }

    private void populateCountriesCache(final List<Country> countries) {
        countries.forEach(countriesCache::put);
        log.info(String.format("Countries and codes cache populated successfully with %d entries", countries.size()));
    }
}
