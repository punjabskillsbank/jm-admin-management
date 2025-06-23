package com.jm_admin_management.serviceImpl;

import com.common.entity.JobPosting;
import com.common.entity.ProposalSubmission;
import com.jm_admin_management.dto.JobPostingReviewDTO;
import com.jm_admin_management.repository.JobPostingRepository;
import com.jm_admin_management.repository.ProposalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminReviewServiceImplTest {

    private JobPostingRepository jobPostingRepository;
    private ProposalRepository proposalRepository;
    private ModelMapper modelMapper;
    private AdminReviewServiceImpl adminReviewService;

    @BeforeEach
    void setUp() {
        jobPostingRepository = mock(JobPostingRepository.class);
        proposalRepository = mock(ProposalRepository.class);
        modelMapper = new ModelMapper();
        adminReviewService = new AdminReviewServiceImpl(modelMapper, jobPostingRepository, proposalRepository);
    }

    @Test
    @DisplayName("Should return job posting with proposals when found")
    void getJobPostingWithProposals_Success() {
        // Arrange
        Long jobPostingId = 1L;
        String jobTitle = "Software Engineer";

        JobPosting jobPosting = new JobPosting();
        jobPosting.setJobPostingId(jobPostingId);
        jobPosting.setTitle(jobTitle);

        ProposalSubmission proposal1 = new ProposalSubmission();
        proposal1.setJobPostingId(jobPostingId.intValue()); // Convert Long to int
        proposal1.setProposedBidAmount(10);

        ProposalSubmission proposal2 = new ProposalSubmission();
        proposal2.setJobPostingId(jobPostingId.intValue()); // Convert Long to int
        proposal2.setProposedBidAmount(20);

        List<ProposalSubmission> proposals = List.of(proposal1, proposal2);

        when(jobPostingRepository.findById(jobPostingId)).thenReturn(Optional.of(jobPosting));
        when(proposalRepository.findByJobPostingId(jobPostingId)).thenReturn(proposals);

        // Act
        JobPostingReviewDTO result = adminReviewService.getJobPostingWithProposals(jobPostingId);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getJobPosting());
        assertEquals(jobPostingId, result.getJobPosting().getJobPostingId());
        assertEquals(jobTitle, result.getJobPosting().getTitle());
        assertEquals(2, result.getProposals().size());
        assertEquals(jobPostingId.intValue(), result.getProposals().get(0).getJobPostingId());
        assertEquals(10, result.getProposals().get(0).getProposedBidAmount());
        assertEquals(20, result.getProposals().get(1).getProposedBidAmount());

        verify(jobPostingRepository).findById(jobPostingId);
        verify(proposalRepository).findByJobPostingId(jobPostingId);
    }

    @Test
    @DisplayName("Should return job posting with empty proposals list")
    void getJobPostingWithProposals_NoProposals() {
        // Arrange
        Long jobPostingId = 1L;
        JobPosting jobPosting = new JobPosting();
        jobPosting.setJobPostingId(jobPostingId);

        when(jobPostingRepository.findById(jobPostingId)).thenReturn(Optional.of(jobPosting));
        when(proposalRepository.findByJobPostingId(jobPostingId)).thenReturn(Collections.emptyList());

        // Act
        JobPostingReviewDTO result = adminReviewService.getJobPostingWithProposals(jobPostingId);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getJobPosting());
        assertEquals(jobPostingId, result.getJobPosting().getJobPostingId());
        assertTrue(result.getProposals().isEmpty());

        verify(jobPostingRepository).findById(jobPostingId);
        verify(proposalRepository).findByJobPostingId(jobPostingId);
    }
}