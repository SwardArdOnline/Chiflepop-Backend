package com.chiflepop.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Prueba E2E del frontend Angular. Requiere tener corriendo:
//   1) el backend (puerto 8080)  2) el frontend (ng serve, puerto 4200)
// Si el frontend NO está corriendo, la prueba se OMITE (no falla).
class FrontendE2ETest {

    private static final String FRONTEND_URL = "http://localhost:4200";

    private WebDriver driver;

    @BeforeEach
    void setUp() throws Exception {
        assumeTrue(frontendEstaCorriendo(), "El frontend no está corriendo en " + FRONTEND_URL);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");   // comenta esta línea para VER el navegador
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void laPaginaPrincipalCarga() {
        driver.get(FRONTEND_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        assertFalse(driver.getTitle().isBlank());

        // Ejemplos para adaptar a tu frontend:
        // driver.findElement(By.id("email")).sendKeys("cliente@correo.com");
        // driver.findElement(By.id("password")).sendKeys("123456");
        // driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    private boolean frontendEstaCorriendo() {
        try {
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
            HttpRequest request = HttpRequest.newBuilder(URI.create(FRONTEND_URL))
                    .timeout(Duration.ofSeconds(2)).GET().build();
            client.send(request, HttpResponse.BodyHandlers.discarding());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}