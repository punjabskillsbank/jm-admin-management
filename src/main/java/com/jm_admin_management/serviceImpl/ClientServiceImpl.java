package com.jm_admin_management.serviceImpl;

import com.jm_admin_management.dto.ClientJobStatsDTO;
import com.jm_admin_management.service.ClientService;
import com.jm_admin_management.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<ClientJobStatsDTO> getClientJobStats() {
        List<Object[]> results = clientRepository.findClientsWithJobCounts();
        Map<UUID, ClientJobStatsDTO> dtoMap = new HashMap<>();

        for (Object[] row : results) {
            UUID clientId = (UUID) row[0];

            ClientJobStatsDTO dto = dtoMap.computeIfAbsent(clientId, id -> {
                ClientJobStatsDTO newDto = new ClientJobStatsDTO();
                newDto.setClientId(id);
                newDto.setProfilePhotoURL((String) row[1]);
                newDto.setIndustry((String) row[2]);
                newDto.setJobCounts(new HashMap<>());
                return newDto;
            });

            String status = (String) row[3];   // job_posting_status
            Long count = ((Number) row[4]).longValue();
            dto.getJobCounts().put(status, count);
        }

        return new ArrayList<>(dtoMap.values());
    }
}
