package com.jm_admin_management.repository;

import com.common.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JobPostingRepository extends JpaRepository<JobPosting,Long>
{
    Optional<JobPosting> findByJobPostingId(Long id);
}
