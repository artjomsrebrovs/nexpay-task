package com.nexpay.task.cache;

import com.nexpay.task.entity.Country;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CountriesCache {

    private final HashMap<String, List<String>> codeCountries;

    public CountriesCache() {
        codeCountries = new HashMap<>();
    }

    public void put(final Country country) {
        final List<String> cashedCountries = codeCountries.get(country.getCode());
        if (cashedCountries != null) {
            cashedCountries.add(country.getCountry());
            codeCountries.put(country.getCode(), cashedCountries);

        } else {
            final List<String> newCountry = new ArrayList<>();
            newCountry.add(country.getCountry());
            codeCountries.put(country.getCode(), newCountry);
        }
    }

    public List<String> get(final String code) {
        final List<String> countries = codeCountries.get(code);

        final List<String> result;
        if (countries != null) {
            result = codeCountries.get(code);
        } else {
            result = new ArrayList<>();
        }

        return Collections.unmodifiableList(result);
    }
}
