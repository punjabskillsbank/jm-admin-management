package com.jm_admin_management.repository;

import com.common.entity.Freelancer;
import com.common.enums.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    List<Freelancer> getFreelancerByProfileStatus(ProfileStatus status);
}
