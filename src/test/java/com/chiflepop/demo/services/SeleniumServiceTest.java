package com.chiflepop.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

// Prueba simple: no levanta Spring ni la base de datos, solo Selenium + Chrome.
class SeleniumServiceTest {

    private final SeleniumService seleniumService = new SeleniumService();

    @Test
    void obtieneElTituloDeExampleCom() {
        String titulo = seleniumService.obtenerTituloDePagina("https://example.com");
        assertEquals("Example Domain", titulo);
    }

    @Test
    void obtieneUnTituloNoVacioDeWikipedia() {
        String titulo = seleniumService.obtenerTituloDePagina("https://www.wikipedia.org");
        assertNotNull(titulo);
        assertFalse(titulo.isBlank());
    }
}