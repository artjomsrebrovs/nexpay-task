package com.nexpay.task.cache;

import com.nexpay.task.entity.Country;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

class CountriesCacheTest {

    private final CountriesCache countriesCache = new CountriesCache();

    @Test
    void testPut() {
        countriesCache.put(new Country("Latvia", "371"));
        final List<String> countries = countriesCache.get("371");

        assertThat("Countries should not be null", countries, is(notNullValue()));
        assertThat("Countries should contain 1 entry", countries.size(), is(1));
        assertThat("Country should be Latvia", countries.get(0), is("Latvia"));
    }

    @Test
    void testPutSameCode() {
        countriesCache.put(new Country("Tanzania", "255"));
        countriesCache.put(new Country("Zanzibar", "255"));
        final List<String> countries = countriesCache.get("255");

        assertThat("Countries should not be null", countries, is(notNullValue()));
        assertThat("Countries should contain 2 entries", countries.size(), is(2));
        assertThat("Country should be Tanzania", countries.get(0), is("Tanzania"));
        assertThat("Country should be Zanzibar", countries.get(1), is("Zanzibar"));
    }

    @Test
    void testGetEmpty() {
        final List<String> countries = countriesCache.get("372");
        assertThat("Countries should not be null", countries, is(notNullValue()));
        assertThat("Countries should contain 0 entries", countries.size(), is(0));
    }

    @Test
    void testGetNonExistent() {
        countriesCache.put(new Country("Latvia", "371"));
        final List<String> countries = countriesCache.get("372");

        assertThat("Countries should not be null", countries, is(notNullValue()));
        assertThat("Countries should contain 0 entries", countries.size(), is(0));
    }

}