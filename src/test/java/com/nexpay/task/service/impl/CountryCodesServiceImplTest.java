package com.nexpay.task.service.impl;

import com.nexpay.task.cache.CountriesCache;
import com.nexpay.task.client.WikimediaClient;
import com.nexpay.task.entity.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CountryCodesServiceImplTest {

    @Mock
    private WikimediaClient wikimediaClient;

    @Mock
    private CountriesCache countriesCache;

    @InjectMocks
    private CountryCodesServiceImpl countryCodesService;

    @Test
    void testPopulateCountryCodes() {
        when(wikimediaClient.getListOfCountryCodesSection()).thenReturn("</span></span><span>&nbsp;</span></span><span>Argentina</span></td><td align=\"right\"><a href=\"/wiki/+54\" title=\"+54\" class=\"mw-redirect\">+54</a></td>");
        countryCodesService.populateCountryCodes();

        final InOrder inOrder = Mockito.inOrder(wikimediaClient, countriesCache);
        inOrder.verify(wikimediaClient, atLeastOnce()).getListOfCountryCodesSection();
        inOrder.verify(countriesCache, atLeastOnce()).put(any(Country.class));
        verifyNoMoreInteractions(wikimediaClient, countriesCache);
    }

    @Test
    void testExtractCountryLines() {
        final String listOfCountryCodesSection = "</tr>\n" +
                "<tr>\n" +
                "<td><span class=\"flagicon\"><span class=\"mw-image-border\"><span><img src=\"//upload.wikimedia.org/wikipedia/commons/thumb/0/06/Flag_of_Zambia.svg/23px-Flag_of_Zambia.svg.png\" decoding=\"async\" data-file-width=\"900\" data-file-height=\"600\" data-file-type=\"drawing\" height=\"15\" width=\"23\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/0/06/Flag_of_Zambia.svg/35px-Flag_of_Zambia.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/0/06/Flag_of_Zambia.svg/45px-Flag_of_Zambia.svg.png 2x\"></span></span><span>&nbsp;</span></span><span>Zambia</span></td>\n" +
                "<td align=\"right\"><a href=\"/wiki/+260\" title=\"+260\" class=\"mw-redirect\">+260</a></td>\n" +
                "<td>UTC+02:00</td>\n" +
                "<td></td></tr>\n" +
                "<tr>\n" +
                "<td><span class=\"flagicon\"><span class=\"mw-image-border\"><span><img src=\"//upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Flag_of_Zanzibar.svg/23px-Flag_of_Zanzibar.svg.png\" decoding=\"async\" data-file-width=\"900\" data-file-height=\"600\" data-file-type=\"drawing\" height=\"15\" width=\"23\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Flag_of_Zanzibar.svg/35px-Flag_of_Zanzibar.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Flag_of_Zanzibar.svg/45px-Flag_of_Zanzibar.svg.png 2x\"></span></span><span>&nbsp;</span></span><span>Zanzibar</span></td>\n" +
                "<td align=\"right\"><a href=\"/wiki/+255\" title=\"+255\" class=\"mw-redirect\">+255 24</a></td>\n" +
                "<td>UTC+03:00</td>\n" +
                "<td></td></tr>\n" +
                "<tr>";

        final List<String> countryLines = countryCodesService.extractCountryLines(listOfCountryCodesSection);
        assertThat("Country lines result should not be null", countryLines, is(notNullValue()));
        assertThat("Country lines result should have 2 entries", countryLines.size(), is(2));
        assertThat("Country lines result 1 should contain Zambia", countryLines.get(0), is("<span>Zambia</span></td><td align=\"right\"><a href=\"/wiki/+260"));
        assertThat("Country lines result 1 should contain Zanzibar", countryLines.get(1), is("<span>Zanzibar</span></td><td align=\"right\"><a href=\"/wiki/+255"));
    }

    @Test
    void testParseCountryLines() {
        final String line1 = "<span>Afghanistan</span></td><td align=\"right\"><a href=\"/wiki/+93";
        final String line2 = "<span>Albania</span></td><td align=\"right\"><a href=\"/wiki/+355";

        final List<String> countryLines = new ArrayList<>();
        countryLines.add(line1);
        countryLines.add(line2);

        final List<Country> countries = countryCodesService.parseCountryLines(countryLines);
        assertThat("Country lines result should not be null", countries, is(notNullValue()));
        assertThat("Country lines result should have 2 entries", countries.size(), is(2));

        final Country country1 = countries.get(0);
        assertThat("Country should not be null", country1, is(notNullValue()));
        assertThat("Country code should br: 93", country1.getCode(), is("93"));
        assertThat("Country name should be: Afghanistan", country1.getCountry(), is("Afghanistan"));

        final Country country2 = countries.get(1);
        assertThat("Country should not be null", country2, is(notNullValue()));
        assertThat("Country code should br: 93", country2.getCode(), is("355"));
        assertThat("Country name should be: Afghanistan", country2.getCountry(), is("Albania"));
    }
}