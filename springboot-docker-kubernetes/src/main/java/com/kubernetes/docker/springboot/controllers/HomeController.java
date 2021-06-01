package com.kubernetes.docker.springboot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public String hello(){
        return "Hello from spring-boot-docker-kubernetes world";
    }
}
