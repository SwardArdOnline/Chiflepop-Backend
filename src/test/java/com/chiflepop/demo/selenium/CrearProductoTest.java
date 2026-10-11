package com.chiflepop.demo.selenium;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CrearProductoTest extends BaseTest {

    // Datos de prueba
    private static final String NOMBRE = "Chifle Selenium " + System.currentTimeMillis();
    private static final String DESCRIPCION = "Producto creado por Selenium";
    private static final String PRECIO = "10.50";
    private static final String PRECIO_EDITADO = "12.50";
    private static final String STOCK = "30";
    private static final String IMAGEN = "https://test.com/chifle.jpg";

    // Selectores reales de admin-products.html
    private static final By BTN_NUEVO = By.xpath("//button[contains(.,'Nuevo Producto')]");
    private static final By CAMPO_IMAGEN = By.id("imagen");
    private static final By CAMPO_NOMBRE = By.id("nombre");
    private static final By CAMPO_DESCRIPCION = By.id("descripcion");
    private static final By CAMPO_PRECIO = By.id("precio");
    private static final By CAMPO_STOCK = By.id("stock");
    private static final By BTN_GUARDAR = By.cssSelector("form button[type='submit']");   // "Crear" / "Actualizar"

    private static By botonDeFila(String textoBoton) {
        return By.xpath("//tr[contains(.,'" + NOMBRE + "')]//button[contains(.,'" + textoBoton + "')]");
    }

    private static boolean apiTieneProductoConPrecio(String precio) {
        String regex = "\"nombre\":\"" + Pattern.quote(NOMBRE) + "\"[^}]*?\"precio\":" + Pattern.quote(precio) + "0?[,}]";
        return Pattern.compile(regex).matcher(productosDesdeApi()).find();
    }

    @Test
    @Order(1)
    void crearProducto() {
        loginComoAdmin();
        driver.get(ADMIN_PRODUCTOS_URL);

        clic(BTN_NUEVO);
        escribir(CAMPO_IMAGEN, IMAGEN);
        escribir(CAMPO_NOMBRE, NOMBRE);
        escribir(CAMPO_DESCRIPCION, DESCRIPCION);
        escribir(CAMPO_PRECIO, PRECIO);
        escribir(CAMPO_STOCK, STOCK);
        clic(BTN_GUARDAR);
        aceptarAlertaSiExiste();
    }

    @Test
    @Order(2)
    void verificarProductoCreado() {
        driver.get(ADMIN_PRODUCTOS_URL);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), NOMBRE));

        assertTrue(apiTieneProductoConPrecio("10.5"), "El producto no aparece en la API con precio 10.50");
    }

    @Test
    @Order(3)
    void editarProducto() {
        driver.get(ADMIN_PRODUCTOS_URL);
        clic(botonDeFila("Editar"));
        escribir(CAMPO_PRECIO, PRECIO_EDITADO);
        clic(BTN_GUARDAR);
        aceptarAlertaSiExiste();

        wait.until(d -> apiTieneProductoConPrecio("12.5"));
        assertTrue(apiTieneProductoConPrecio("12.5"), "El precio no se actualizó en la API");
    }

    @Test
    @Order(4)
    void eliminarProducto() {
        driver.get(ADMIN_PRODUCTOS_URL);
        clic(botonDeFila("Eliminar"));
        aceptarAlertaSiExiste();
        aceptarAlertaSiExiste(); 
        wait.until(d -> !productosDesdeApi().contains(NOMBRE));
        assertFalse(productosDesdeApi().contains(NOMBRE), "El producto sigue en la API");
    }
}