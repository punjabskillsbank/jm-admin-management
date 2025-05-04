package com.jm_admin_management.exceptionHandling;

import java.util.UUID;

public class FreelancerNotFoundException extends RuntimeException {
    public FreelancerNotFoundException(UUID freelancerId) {
        super("Freelancer with ID " + freelancerId + " not found");
    }
}
