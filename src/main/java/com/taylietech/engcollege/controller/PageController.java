package com.taylietech.engcollege.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {


    String pageName;


    @RequestMapping("/")
    public String getIndexPage(Model model) {

        pageName="Home";
        model.addAttribute("activeHome", true);
        model.addAttribute("home", pageName);
        return "index";
    }


    @RequestMapping("/about")
    public String getAboutPage(Model model) {

        pageName="About";
        model.addAttribute("activeAbout", true);
        model.addAttribute("about", pageName);
        return "about";
    }

    @RequestMapping("/innovation")
    public String getInnovationPage(Model model) {

        pageName="Programs";
        model.addAttribute("activePrograms", true);
        model.addAttribute("programs", pageName);
        return "innovation";
    }

    @RequestMapping("/research")
    public String getResearchPage(Model model) {

        pageName="Programs";
        model.addAttribute("activePrograms", true);
        model.addAttribute("programs", pageName);
        return "research";
    }

    @RequestMapping("/stem")
    public String getStemPage(Model model) {

        pageName="Programs";
        model.addAttribute("activePrograms", true);
        model.addAttribute("programs", pageName);
        return "stem";
    }

    @RequestMapping("/programs")
    public String getProgramsPage(Model model) {

        pageName="Programs";
        model.addAttribute("activePrograms", true);
        model.addAttribute("programs", pageName);
        return "programs";
    }

    @RequestMapping("/resources")
    public String getResourcesPage(Model model) {

        pageName="Resources";
        model.addAttribute("activeResources", true);
        model.addAttribute("resources", pageName);
        return "resources";
    }

    @RequestMapping("/blog")
    public String getBlog(Model model) {

        pageName="Blog";
        model.addAttribute("activeResources", true);
        model.addAttribute("resources", pageName);
        return "blog";
    }

    @RequestMapping("/campus")
    public String getCommunityPage(Model model) {

        pageName="Campus";
        model.addAttribute("activeCampus", true);
        model.addAttribute("campus", pageName);
        return "campus";
    }

    @RequestMapping("/resetPasswordPage")
    public String getForgotPasswordPage() {
        return "/auth/resetPassword";
    }

}
