package com.jm_admin_management.controller;

import com.jm_admin_management.dto.ClientJobStatsDTO;
import com.jm_admin_management.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/stats")
    public ResponseEntity<List<ClientJobStatsDTO>> getClientJobStats() {
        return ResponseEntity.ok(clientService.getClientJobStats());
    }
}
