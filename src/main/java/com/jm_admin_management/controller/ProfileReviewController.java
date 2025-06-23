package com.jm_admin_management.controller;

import com.common.dto.ProposalSubmissionDTO;
import com.common.entity.Freelancer;
import com.jm_admin_management.dto.JobPostingReviewDTO;
import com.jm_admin_management.dto.UpdateProfileStatusRequest;
import com.jm_admin_management.service.AdminReviewService;
import com.jm_admin_management.service.FreelancerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/admin_management")
@RequiredArgsConstructor
public class ProfileReviewController {

    private final FreelancerProfileService freelancerProfileService;
    private final ModelMapper modelMapper;
    private final AdminReviewService adminReviewService;



    @GetMapping("/pending_freelancers")
    public ResponseEntity<List<Freelancer>> getPendingFreelancers() {
        List<Freelancer> pendingFreelancers = freelancerProfileService.getPendingFreelancers();
        return ResponseEntity.ok(pendingFreelancers);
    }

    @GetMapping("/{jobPostingId}")
    public ResponseEntity<JobPostingReviewDTO> getJobPostingWithProposals(@PathVariable Long jobPostingId) {
            JobPostingReviewDTO reviewDTO = adminReviewService.getJobPostingWithProposals(jobPostingId);
            return ResponseEntity.ok(reviewDTO);

    }

    @PatchMapping("/freelancers/{freelancerId}/update_profile_status")
    public ResponseEntity<String> updateProfileStatus(
            @PathVariable UUID freelancerId, @RequestBody @Valid UpdateProfileStatusRequest request){
        freelancerProfileService.updateProfileStatus(freelancerId, request.getProfileStatus());
        return ResponseEntity.ok("Freelancer profile status updated successfully.");
    }
}
