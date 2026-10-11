package com.chiflepop.demo.selenium;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.net.InetSocketAddress;
import java.net.Socket;
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

/**
 * Clase base de todas las pruebas de Selenium.
 * Abre Chrome una vez por clase de prueba y lo cierra al terminar.
 *
 * REQUIERE TENER CORRIENDO: MySQL, el backend (8080) y el frontend Angular (4200).
 * Si no están corriendo, las pruebas se OMITEN (no fallan).
 */
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

    // Pantalla de productos del admin (app.routes.ts: admin/products)
    protected static final String ADMIN_PRODUCTOS_URL = FRONTEND_URL + "/admin/products";

    // Pausa entre acciones (milisegundos) para poder VER lo que hace el navegador.
    // Para ir rápido:  -Dpausa=0
    protected static final long PAUSA_MS = Long.getLong("pausa", 500L);

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    @BeforeAll
    protected static void abrirNavegador() {
        assumeTrue(estaCorriendo(FRONTEND_URL), "El frontend no está corriendo en " + FRONTEND_URL);
        assumeTrue(estaCorriendo(BACKEND_URL + "/api/productos"), "El backend no está corriendo en " + BACKEND_URL);

        ChromeOptions options = new ChromeOptions();
        if (Boolean.getBoolean("headless")) {          // -Dheadless=true para no mostrar la ventana
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

    // ===== Ayudas reutilizables =====

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
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);   // limpia el campo
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
            // no había alerta, seguimos
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

    /** Lee los productos directo de la API (como harías en Postman). */
    protected static String productosDesdeApi() {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(BACKEND_URL + "/api/productos")).GET().build();
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo consultar la API de productos", e);
        }
    }

    /**
     * Comprueba si hay algo escuchando en el puerto de la URL.
     * Prueba localhost, 127.0.0.1 y ::1, porque Angular a veces escucha solo en IPv6 (::1)
     * y Java intentaba únicamente IPv4.
     */
    private static boolean estaCorriendo(String url) {
        int puerto = URI.create(url).getPort();
        for (String host : new String[] { "localhost", "127.0.0.1", "::1" }) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(host, puerto), 2000);
                return true;
            } catch (Exception e) {
                // probamos con la siguiente dirección
            }
        }
        return false;
    }
}
