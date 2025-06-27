package com.jm_admin_management.service;

import com.jm_admin_management.dto.JobPostingReviewDTO;

public interface JobProposalReviewService {
    JobPostingReviewDTO getJobPostingWithProposals(Long jobPostingId);
}
