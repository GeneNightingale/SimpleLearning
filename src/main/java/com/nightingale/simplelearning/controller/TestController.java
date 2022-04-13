package com.nightingale.simplelearning.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/api/hellouser")
    public String getUser()
    {
        return "Hello User";
    }

    @RequestMapping("/api/hellostudent")
    public String getStudent()
    {
        return "Hello Student";
    }

    @RequestMapping("/api/helloteacher")
    public String getTeacher()
    {
        return "Hello Teacher";
    }

    @RequestMapping("/api/helloadmin")
    public String getAdmin()
    {
        return "Hello Admin";
    }

}