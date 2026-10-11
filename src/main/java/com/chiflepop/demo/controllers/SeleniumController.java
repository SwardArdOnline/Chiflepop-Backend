package com.chiflepop.demo.controllers;

import com.chiflepop.demo.services.SeleniumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/selenium")   
public class SeleniumController {

    @Autowired
    private SeleniumService seleniumService;

    @GetMapping("/scraping")
    public String probarSelenium(@RequestParam(defaultValue = "https://www.google.com") String url) {
        String titulo = seleniumService.obtenerTituloDePagina(url);
        return "El título de la página es: " + titulo;
    }
}