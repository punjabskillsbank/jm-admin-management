package com.jm_admin_management.repository;

import com.common.entity.Freelancer;
import com.common.enums.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, UUID> {
    List<Freelancer> getFreelancersByProfileStatus(ProfileStatus status);
}
