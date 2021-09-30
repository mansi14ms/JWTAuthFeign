package com.userservice.user.controller;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    //Unauthenticated
    //Changed in WebSecurityConfig
    @RequestMapping(value = "/assignment", method = RequestMethod.GET)
    public String hello() { return "Hello User"; }

    //Authorized API
    @GetMapping("/hello")
    public String helloWorld() { return "FromContoller"; }


}

