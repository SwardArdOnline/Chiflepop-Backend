package com.chiflepop.demo.selenium;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginAdminTest extends BaseTest {

    @BeforeEach
    void empezarSinSesion() {
        cerrarSesion();
    }

    @Test
    void loginAdminCorrecto() {
        loginComoAdmin();
        assertTrue(driver.getCurrentUrl().contains("/admin/products"),
                "El admin debería terminar en /admin/products");
    }

    @Test
    void loginConPasswordIncorrectoSeQuedaEnLogin() {
        driver.get(FRONTEND_URL + "/login");
        escribir(LOGIN_EMAIL, ADMIN_EMAIL);
        escribir(LOGIN_PASSWORD, "clave-incorrecta");
        clic(LOGIN_BOTON);
        aceptarAlertaSiExiste();
        assertTrue(driver.getCurrentUrl().contains("/login"), "Debería seguir en /login");
    }
}
