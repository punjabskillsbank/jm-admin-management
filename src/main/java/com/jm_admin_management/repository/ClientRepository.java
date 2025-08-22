package com.jm_admin_management.repository;

import com.common.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    @Query(value = """
        SELECT c.client_id, c.profile_photo_url, c.industry, c.company_name, j.job_posting_status, COUNT(j.job_posting_id) 
        FROM clients c
        LEFT JOIN job_postings j ON c.client_id = j.client_id
        GROUP BY c.client_id, c.profile_photo_url, c.industry, c.company_name, j.job_posting_status
        """, nativeQuery = true)
    List<Object[]> findClientsWithJobCounts();
}
