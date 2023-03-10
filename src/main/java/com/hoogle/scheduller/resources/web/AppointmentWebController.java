package com.hoogle.scheduller.resources.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("home")
public class AppointmentWebController {

    @GetMapping
    public String getHomePage(Model model) {
        List<String> words = List.of("banana", "uva", "pera", "laranja");
        model.addAttribute("fruits", words);
        return "home";
    }

}

// json = javascript object notation
