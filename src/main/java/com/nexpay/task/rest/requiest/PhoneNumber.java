package com.nexpay.task.rest.requiest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumber {

    @NotNull(message = "Country code should not be null")
    @Pattern(regexp = "[0-9]+", message = "Country code should be numeric")
    @Size(max = 5, message = "Country code is too long")
    @Size(min = 2, message = "Country code is too short")
    private String code;

    @NotNull(message = "Phone number should not be null")
    @Pattern(regexp = "[0-9]+", message = "Phone number should be numeric")
    @Size(max = 15, message = "Phone number code is too long")
    @Size(min = 5, message = "Phone number is too short")
    private String number;
}
