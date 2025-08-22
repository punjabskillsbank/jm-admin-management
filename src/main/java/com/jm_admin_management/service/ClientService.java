package com.jm_admin_management.service;

import com.jm_admin_management.dto.ClientJobStatsDTO;
import java.util.List;

public interface ClientService {
    List<ClientJobStatsDTO> getClientJobStats();
}
