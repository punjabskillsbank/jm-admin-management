package com.jm_admin_management.exceptionHandling;

public class NoPendingFreelancerException extends RuntimeException {
    public NoPendingFreelancerException() {
        super("No pending freelancer found");
    }
}
