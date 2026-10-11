package com.chiflepop.demo.services;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

@Service
public class SeleniumService {

    public String obtenerTituloDePagina(String url) {
        // Opciones del navegador
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");           // sin ventana. Comenta esta línea si quieres VER el navegador
        options.addArguments("--no-sandbox");             // necesario en Docker / servidores Linux
        options.addArguments("--disable-dev-shm-usage");  // evita errores de memoria en Docker

        // Selenium Manager descarga el driver automáticamente
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(url);

            String titulo = driver.getTitle();
            // WebElement elemento = driver.findElement(By.id("mi-id"));

            return titulo;
        } finally {
            driver.quit();
        }
    }
}
