package com.netdisk.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping("/login/")
    public String login(){
        System.out.println("login success");
        return "login succuss";
    }
}
