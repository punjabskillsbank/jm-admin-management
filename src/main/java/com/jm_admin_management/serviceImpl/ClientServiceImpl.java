package com.jm_admin_management.serviceImpl;

import com.common.enums.JobPostingStatus;
import com.jm_admin_management.dto.ClientJobProjectionDTO;
import com.jm_admin_management.dto.ClientJobStatsDTO;
import com.jm_admin_management.repository.ClientRepository;
import com.jm_admin_management.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Service
@RequiredArgsConstructor // generates constructor for all final fields
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ClientJobStatsDTO> getClientJobStats() {
        List<ClientJobProjectionDTO> projections = clientRepository.findClientsWithJobStats();
        Map<UUID, ClientJobStatsDTO> clientStatsMap = new HashMap<>();

        for (ClientJobProjectionDTO projection : projections) {
            UUID clientId = projection.getClientId();

            ClientJobStatsDTO clientStats = clientStatsMap.computeIfAbsent(clientId, id -> {
                ClientJobStatsDTO mappedStats = modelMapper.map(projection, ClientJobStatsDTO.class);

                Map<JobPostingStatus, Long> jobCounts = new EnumMap<>(JobPostingStatus.class);
                for (JobPostingStatus status : JobPostingStatus.values()) {
                    jobCounts.put(status, 0L);
                }
                mappedStats.setJobCounts(jobCounts);
                return mappedStats;
            });

            clientStats.getJobCounts().put(projection.getStatus(), projection.getCount());
        }

        return new ArrayList<>(clientStatsMap.values());
    }
}
