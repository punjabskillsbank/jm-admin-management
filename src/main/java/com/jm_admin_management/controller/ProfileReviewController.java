package com.jm_admin_management.controller;

import com.common.entity.Freelancer;
import com.jm_admin_management.dto.UpdateProfileStatusRequest;
import com.jm_admin_management.service.FreelancerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin_management")
@RequiredArgsConstructor
public class ProfileReviewController {

    private final FreelancerProfileService freelancerProfileService;

    @GetMapping("/pending_freelancers")
    public ResponseEntity<List<Freelancer>> getPendingFreelancers() {
        List<Freelancer> pendingFreelancers = freelancerProfileService.getPendingFreelancers();
        return ResponseEntity.ok(pendingFreelancers);
    }

    @PatchMapping("/freelancers/{freelancerId}/status")
    public ResponseEntity<String> updateFreelancerStatus(
            @PathVariable UUID freelancerId, @RequestBody @Valid UpdateProfileStatusRequest request){
        freelancerProfileService.updateProfileStatus(freelancerId, request.getProfileStatus());
        return ResponseEntity.ok("Freelancer profile status updated successfully.");
    }
}
