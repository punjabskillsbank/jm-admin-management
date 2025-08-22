package com.jm_admin_management.serviceImpl;

import com.common.enums.JobPostingStatus;
import com.jm_admin_management.dto.ClientJobStatsDTO;
import com.jm_admin_management.repository.ClientRepository;
import com.jm_admin_management.service.ClientService;
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
            String profilePhotoURL = (String) row[1];
            String industry = (String) row[2];
            String companyName = (String) row[3];
            String statusStr = row[4] != null ? row[4].toString() : null;
            Long count = row[5] != null ? ((Number) row[5]).longValue() : 0L;

            ClientJobStatsDTO dto = dtoMap.computeIfAbsent(clientId, id -> {
                ClientJobStatsDTO newDto = new ClientJobStatsDTO();
                newDto.setClientId(id);
                newDto.setProfilePhotoURL(profilePhotoURL);
                newDto.setIndustry(industry);
                newDto.setCompanyName(companyName);

                //  Initialize all enum values with 0
                Map<JobPostingStatus, Long> jobCounts = new EnumMap<>(JobPostingStatus.class);
                for (JobPostingStatus s : JobPostingStatus.values()) {
                    jobCounts.put(s, 0L);
                }
                newDto.setJobCounts(jobCounts);

                return newDto;
            });

            // Update only actual status count
            if (statusStr != null) {
                JobPostingStatus status = JobPostingStatus.valueOf(statusStr);
                dto.getJobCounts().put(status, count);
            }
        }

        return new ArrayList<>(dtoMap.values());
    }
}
