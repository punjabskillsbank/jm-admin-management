package com.jm_admin_management.repository;

import com.common.entity.ProposalSubmission;
import com.common.enums.ProposalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProposalRepository extends JpaRepository<ProposalSubmission, Long> {

    List<ProposalSubmission> findByJobPostingId(Long jobPostingId);

}
