package com.taylietech.engcollege.controller;


import com.taylietech.engcollege.util.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class PageController {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailConstructor mailConstructor;

    String pageName;

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public String sendMessage(Model model,
                           @ModelAttribute("name") String name,
                           @ModelAttribute("email") String email,
                           @ModelAttribute("phone") String phone,
                           @ModelAttribute("subject") String subject,
                           @ModelAttribute("message") String message) throws Exception {

        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("phone", phone);
        model.addAttribute("subject", subject);
        model.addAttribute("message", message);


        SimpleMailMessage userEmail = mailConstructor.constructMessageEmail(name, email, phone, subject, message);
        javaMailSender.send(userEmail);
        model.addAttribute("messageSent", "true");

        return "/contact";
    }



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
