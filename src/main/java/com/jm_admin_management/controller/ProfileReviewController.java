package com.jm_admin_management.controller;

import com.common.entity.Freelancer;
import com.jm_admin_management.service.FreelancerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
