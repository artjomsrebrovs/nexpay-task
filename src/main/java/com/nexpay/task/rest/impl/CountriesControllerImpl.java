package com.nexpay.task.rest.impl;

import com.nexpay.task.cache.CountriesCache;
import com.nexpay.task.rest.CountriesController;
import com.nexpay.task.rest.requiest.PhoneNumber;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountriesControllerImpl implements CountriesController {

    private final CountriesCache countriesCache;

    @Autowired
    public CountriesControllerImpl(final CountriesCache countriesCache) {
        this.countriesCache = countriesCache;
    }

    @PostMapping("/country")
    public ResponseEntity<List<String>> getCountryByCode(@Valid @RequestBody final PhoneNumber phoneNumber, final Errors errors) {
        final ResponseEntity<List<String>> response;
        final List<String> errorMessages = new ArrayList<>();

        if (errors.hasErrors()) {
            final List<ObjectError> allErrors = errors.getAllErrors();
            for (final ObjectError objectError : allErrors) {
                errorMessages.add(objectError.getDefaultMessage());
            }

            response = new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);

        } else {
            final List<String> countries = countriesCache.get(phoneNumber.getCode());
            response = new ResponseEntity<>(countries, HttpStatus.OK);
        }

        return response;
    }
}
