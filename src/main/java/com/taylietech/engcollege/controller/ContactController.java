package com.taylietech.engcollege.controller;

import com.taylietech.engcollege.util.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailConstructor mailConstructor;

    @PostMapping
    public String sendMessage(Model model,
                              @ModelAttribute("name") String name,
                              @ModelAttribute("email") String email,
                              @ModelAttribute("phone") String phone,
                              @ModelAttribute("subject") String subject,
                              @ModelAttribute("message") String message) {

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



    @GetMapping
    public String getContactPage(Model model) {

        String contact ="Contact";
        model.addAttribute("activeContact", true);
        model.addAttribute("contact", contact);
        return "contact";
    }
}
