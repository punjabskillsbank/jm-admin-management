package com.jm_admin_management.service;

import com.common.entity.Freelancer;
import com.common.enums.ProfileStatus;

import java.util.List;
import java.util.UUID;

public interface FreelancerProfileService {
    List<Freelancer> getPendingFreelancers();
    void updateProfileStatus(UUID freelancerId, ProfileStatus newStatus);
}
