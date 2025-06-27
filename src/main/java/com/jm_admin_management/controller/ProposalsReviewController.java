package com.jm_admin_management.controller;

import com.jm_admin_management.dto.JobPostingReviewDTO;
import com.jm_admin_management.service.JobProposalReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProposalsReviewController {
    private final JobProposalReviewService jobProposalReviewService;

    @GetMapping("/{jobPostingId}")
    public ResponseEntity<JobPostingReviewDTO> getJobPostingWithProposals(@PathVariable Long jobPostingId) {
        JobPostingReviewDTO reviewDTO = jobProposalReviewService.getJobPostingWithProposals(jobPostingId);
        return ResponseEntity.ok(reviewDTO);
    }
}
