package com.jm_admin_management.serviceImpl;

import com.common.entity.Freelancer;
import com.common.enums.ProfileStatus;
import com.jm_admin_management.exceptionHandling.FreelancerNotFoundException;
import com.jm_admin_management.repository.FreelancerRepository;
import com.jm_admin_management.repository.JobPostingRepository;
import com.jm_admin_management.repository.ProposalRepository;
import com.jm_admin_management.service.FreelancerProfileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FreelancerProfileServiceImpl implements FreelancerProfileService {

    private final FreelancerRepository freelancerRepository;

    @Override
    public List<Freelancer> getPendingFreelancers() {

        List<Freelancer> freelancers = freelancerRepository.getFreelancersByProfileStatus(ProfileStatus.PENDING);
        if (freelancers.isEmpty()) {
            return Collections.emptyList();
        }
        return freelancers;
    }

    @Override
    public void updateProfileStatus(UUID freelancerId, ProfileStatus newStatus) {
        Freelancer freelancer = freelancerRepository.findById(freelancerId)
                .orElseThrow(() -> new FreelancerNotFoundException(freelancerId));

        freelancer.setProfileStatus(newStatus);
        freelancerRepository.save(freelancer);
    }


}
