package com.currencyconversionservice.currencyconversion.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/assignment")
    public String hello() { return "Hello User"; }


}
