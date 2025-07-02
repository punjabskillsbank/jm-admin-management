package com.jm_admin_management.service;

import com.jm_admin_management.dto.JobPostingProposalsReviewDTO;

public interface JobPostingProposalsReviewService {
    JobPostingProposalsReviewDTO getJobPostingWithProposals(Long jobPostingId);
}
