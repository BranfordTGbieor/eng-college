package com.taylietech.engcollege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class PageController {

    String pageName;

    @RequestMapping("/")
    public String Index(Model model) {

        pageName="Home";
        model.addAttribute("activeHome", true);
        model.addAttribute("home", pageName);
        return "index";
    }

    @RequestMapping("/about")
    public String About(Model model) {

        pageName="About";
        model.addAttribute("activeAbout", true);
        model.addAttribute("about", pageName);
        return "about";
    }

    @RequestMapping("/contact")
    public String Contact(Model model) {

        pageName="Contact";
        model.addAttribute("activeContact", true);
        model.addAttribute("contact", pageName);
        return "contact";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        return "/auth/register";
    }


    @RequestMapping("/resetPasswordPage")
    public String forgotPasswordPage() {
        return "/auth/resetPassword";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "/auth/login";
    }

}
