package com.nexpay.task.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber {

    @NotNull(message = "Country code should not be null")
    @Pattern(regexp = "[0-9]+", message = "Country code should be numeric")
    @Size(max = 5, message = "Country code is too long")
    @Size(min = 2, message = "Country code is too short")
    private String code;

    @NotNull(message = "Phone number should not be null")
    @Pattern(regexp = "[0-9]+", message = "Phone number should be numeric")
    @Size(max = 15, message = "Phone number is too long")
    @Size(min = 5, message = "Phone number is too short")
    private String number;
}
