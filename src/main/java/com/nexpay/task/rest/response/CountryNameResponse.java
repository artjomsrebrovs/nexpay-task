package com.nexpay.task.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryNameResponse {

    private List<String> countries;

    private List<String> errors;

    public static CountryNameResponse SUCCESS(final List<String> countries) {
        return new CountryNameResponse(countries, null);
    }

    public static CountryNameResponse FAIL(final List<String> errors) {
        return new CountryNameResponse(null, errors);
    }
}
