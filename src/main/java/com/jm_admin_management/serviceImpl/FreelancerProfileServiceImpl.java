package com.jm_admin_management.serviceImpl;

import com.common.entity.Freelancer;
import com.common.enums.ProfileStatus;
import com.jm_admin_management.repository.FreelancerRepository;
import com.jm_admin_management.service.FreelancerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreelancerProfileServiceImpl implements FreelancerProfileService {

    private final FreelancerRepository freelancerRepository;

    @Override
    public List<Freelancer> getPendingFreelancers() {

        List<Freelancer> freelancers = freelancerRepository.getFreelancerByProfileStatus(ProfileStatus.PENDING);
        if (freelancers.isEmpty()) {
            throw new RuntimeException("No freelancers found with PENDING status");
        }
        return freelancers;
    }
}
