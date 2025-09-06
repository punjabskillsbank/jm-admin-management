package com.jm_admin_management.repository;

import com.common.entity.Client;
import com.jm_admin_management.dto.ClientJobProjectionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    @Query(value = """
            SELECT new com.jm_admin_management.dto.ClientJobProjectionDTO(
                c.clientId,
                c.profilePhotoURL,
                c.industry,
                c.companyName,
                j.jobPostingStatus,
                COUNT(j.jobPostingId)
            )
            FROM Client c
            LEFT JOIN JobPosting j ON j.clientId = c.clientId
            GROUP BY c.clientId, c.profilePhotoURL, c.industry, c.companyName, j.jobPostingStatus
            """)
    List<ClientJobProjectionDTO> findClientsWithJobStats();
}
