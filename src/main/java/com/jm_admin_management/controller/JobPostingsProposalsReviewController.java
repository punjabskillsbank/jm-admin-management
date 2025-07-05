package com.jm_admin_management.controller;

import com.jm_admin_management.dto.JobPostingProposalsReviewDTO;
import com.jm_admin_management.service.JobPostingProposalsReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin_management/job_postings")
public class JobPostingsProposalsReviewController {
    private final JobPostingProposalsReviewService jobPostingProposalsReviewService;

    @GetMapping("/{jobPostingId}/proposals")
    public ResponseEntity<JobPostingProposalsReviewDTO> getJobPostingWithProposals(@PathVariable Long jobPostingId) {
        JobPostingProposalsReviewDTO reviewDTO = jobPostingProposalsReviewService.getJobPostingWithProposals(jobPostingId);
        return ResponseEntity.ok(reviewDTO);
    }
}
