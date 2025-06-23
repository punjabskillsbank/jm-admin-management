package com.jm_admin_management.exceptionHandling;

public class JobPostingNotFoundException extends RuntimeException {
    public JobPostingNotFoundException(Long jobPostingId) {
        super("Job Posting with ID " + jobPostingId + " not found");
    }
}