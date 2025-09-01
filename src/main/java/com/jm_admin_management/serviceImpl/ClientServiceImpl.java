package com.jm_admin_management.serviceImpl;

import com.common.enums.JobPostingStatus;
import com.jm_admin_management.dto.ClientJobProjectionDTO;
import com.jm_admin_management.dto.ClientJobStatsDTO;
import com.jm_admin_management.repository.ClientRepository;
import com.jm_admin_management.service.ClientService;
import com.jm_admin_management.config.ModelMapperConfig;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ClientJobStatsDTO> getClientJobStats() {
        List<ClientJobProjectionDTO> rows = clientRepository.findClientsWithJobStats();
        Map<UUID, ClientJobStatsDTO> dtoMap = new HashMap<>();

        for (ClientJobProjectionDTO row : rows) {
            UUID clientId = row.getClientId();

            ClientJobStatsDTO dto = dtoMap.computeIfAbsent(clientId, id -> {
                ClientJobStatsDTO newDto = modelMapper.map(row, ClientJobStatsDTO.class);

                Map<JobPostingStatus, Long> counts = new EnumMap<>(JobPostingStatus.class);
                for (JobPostingStatus s : JobPostingStatus.values()) {
                    counts.put(s, 0L);
                }
                newDto.setJobCounts(counts);
                return newDto;
            });

            dto.getJobCounts().put(row.getStatus(), row.getCount());
        }

        return new ArrayList<>(dtoMap.values());
    }
}
