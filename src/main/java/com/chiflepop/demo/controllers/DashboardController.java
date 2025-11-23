package com.chiflepop.demo.controllers;

import com.chiflepop.demo.dto.DashboardStatsDTO;
import com.chiflepop.demo.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getStats(@RequestParam Integer clienteId) {
        return ResponseEntity.ok(dashboardService.obtenerEstadisticas(clienteId));
    }
}