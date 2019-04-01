package com.example.sweater.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
/**
This controller returns the main page
 */
@Controller
public class MainController {
    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

}
