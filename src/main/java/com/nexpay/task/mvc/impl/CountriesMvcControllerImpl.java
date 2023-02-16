package com.nexpay.task.mvc.impl;

import com.nexpay.task.mvc.CountriesMvcController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CountriesMvcControllerImpl implements CountriesMvcController {

    @GetMapping("/")
    public String viewBooks(final Model model) {
        return "index";
    }
}
