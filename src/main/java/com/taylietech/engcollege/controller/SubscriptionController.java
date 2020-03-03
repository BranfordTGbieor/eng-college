package com.taylietech.engcollege.controller;

import com.taylietech.engcollege.util.SubscribeHandler;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/subscribe")
public class SubscriptionController {

    Log LOG = LogFactory.getLog(SubscriptionController.class);

    @Autowired
    SubscribeHandler subscribeHandler;

    @PostMapping
    public String subscribe(Model model, @ModelAttribute("email") String email) throws Exception {

        model.addAttribute("email", email);
        subscribeHandler.subscribe(email);

        return "/fragments/subscription";
    }


    @GetMapping
    public String getSubscriptionPage(Model model) {

        return "/auth/subscription";
    }
}
