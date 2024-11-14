package org.example.api_gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class MainPage {


    @GetMapping("")
    public String hello() {
        return "¡Esta es una prueba base del API Gateway!";}
}
