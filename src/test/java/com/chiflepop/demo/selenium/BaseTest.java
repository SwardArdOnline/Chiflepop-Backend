package com.chiflepop.demo.selenium;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseTest {

    protected static final String FRONTEND_URL = "http://localhost:4200";
    protected static final String BACKEND_URL = "http://localhost:8080";

    // Usuario admin creado por DataInitializer del backend
    protected static final String ADMIN_EMAIL = "admin@chiflepop.com";
    protected static final String ADMIN_PASSWORD = "admin123";

    // Selectores reales de login.component.html
    protected static final By LOGIN_EMAIL = By.id("email");
    protected static final By LOGIN_PASSWORD = By.id("password");
    protected static final By LOGIN_BOTON = By.cssSelector("button.btn-submit");

    // Pantalla de productos del admin 
    protected static final String ADMIN_PRODUCTOS_URL = FRONTEND_URL + "/admin/products";

    protected static final long PAUSA_MS = Long.getLong("pausa", 500L);

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    @BeforeAll
    protected static void abrirNavegador() {
        assumeTrue(estaCorriendo(FRONTEND_URL), "El frontend no está corriendo en " + FRONTEND_URL);
        assumeTrue(estaCorriendo(BACKEND_URL + "/api/productos"), "El backend no está corriendo en " + BACKEND_URL);

        ChromeOptions options = new ChromeOptions();
        if (Boolean.getBoolean("headless")) {          
            options.addArguments("--headless=new");
        }
        options.addArguments("--start-maximized");
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false));
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    protected static void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    protected static void pausa(long ms) {
        if (ms <= 0) {
            return;
        }
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected static WebElement esperarVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected static void escribir(By by, String texto) {
        WebElement el = esperarVisible(by);
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);// limpia el campo
        el.sendKeys(texto);
        pausa(PAUSA_MS);
    }

    protected static void clic(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
        pausa(PAUSA_MS);
    }

    protected static void aceptarAlertaSiExiste() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.alertIsPresent())
                    .accept();
        } catch (Exception e) {
        }
        pausa(PAUSA_MS);
    }

    protected static void loginComoAdmin() {
        driver.get(FRONTEND_URL + "/login");
        escribir(LOGIN_EMAIL, ADMIN_EMAIL);
        escribir(LOGIN_PASSWORD, ADMIN_PASSWORD);
        clic(LOGIN_BOTON);
        wait.until(ExpectedConditions.urlContains("/admin"));
    }

    protected static void cerrarSesion() {
        driver.get(FRONTEND_URL);
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript(
                "window.localStorage.clear(); window.sessionStorage.clear();");
    }

    protected static String productosDesdeApi() {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(BACKEND_URL + "/api/productos")).GET().build();
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo consultar la API de productos", e);
        }
    }

    private static boolean estaCorriendo(String url) {
        try {
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .timeout(Duration.ofSeconds(2)).GET().build();
            client.send(request, HttpResponse.BodyHandlers.discarding());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
