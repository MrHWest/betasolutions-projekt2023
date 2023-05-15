package com.betasolutions.projekt2023.controller;
@Controller()
public class Controller {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
