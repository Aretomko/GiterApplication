package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;
/**
This controller is for processing the registration and activation requests using the logic which is specified in the Autowired service
 */
@Controller
public class RegistrationController {
    @Autowired
    private UserSevice userSevice;
    /**
    This method displays the registration page;
     */
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }
    /**
    This method displays errors of registration or redirect the user tj the login page if the registration was successful
     */
    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        if (!userSevice.addUser(user)) {
            model.put("message", "User exists!");
            return "registration";
        }
        model.put("message", "Check your mail for the activation code");
        return "login";
    }
    /**
    This method serves the mapping which is accessed from the mail of registration confirmation, it shows to the user whenever the activation was successful or not
     */
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userSevice.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}
