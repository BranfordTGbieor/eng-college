package com.taylietech.engcollege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DepartmentsController {

    @RequestMapping("/electrical")
    public String getElectrical() {
        return "/departments/electrical/electrical";
    }

    @RequestMapping("/civil")
    public String getCivil() {
        return "/departments/civil/civil";
    }

    @RequestMapping("/geology")
    public String getGeology() {
        return "/departments/geology/geology";
    }

    @RequestMapping("/mining")
    public String getMining() {
        return "/departments/mining/mining";
    }
}
