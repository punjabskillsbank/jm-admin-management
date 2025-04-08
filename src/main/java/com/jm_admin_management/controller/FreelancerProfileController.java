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
@RequestMapping("/api/freelancer")
@RequiredArgsConstructor
public class FreelancerProfileController {

    private final FreelancerProfileService freelancerProfileService;

    @GetMapping("/getPendingFreelancers")
    public ResponseEntity<List<Freelancer>> getPendingFreelancers() {
        List<Freelancer> pendingFreelancers = freelancerProfileService.getPendingFreelancers();
        return ResponseEntity.ok(pendingFreelancers);
    }


}
