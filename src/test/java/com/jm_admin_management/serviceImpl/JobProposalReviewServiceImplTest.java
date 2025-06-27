package com.jm_admin_management.serviceImpl;

import com.common.entity.JobPosting;
import com.common.entity.ProposalSubmission;
import com.common.exceptionHandling.JobPostingNotFoundException;
import com.jm_admin_management.dto.JobPostingReviewDTO;
import com.jm_admin_management.repository.JobPostingRepository;
import com.jm_admin_management.repository.ProposalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobProposalReviewServiceImplTest {

    @Mock
    private JobPostingRepository jobPostingRepository;

    @Mock
    private ProposalRepository proposalRepository;

    private ModelMapper modelMapper;

    @InjectMocks
    private JobProposalReviewServiceImpl adminReviewService;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        adminReviewService = new JobProposalReviewServiceImpl(modelMapper, jobPostingRepository, proposalRepository);
    }

    @Test
    @DisplayName("Should return job posting with proposals when found")
    void getJobPostingWithProposals_Success() {
        Long jobPostingId = 1L;

        JobPosting jobPosting = new JobPosting();
        jobPosting.setJobPostingId(jobPostingId);
        jobPosting.setTitle("Software Engineer");

        ProposalSubmission proposal1 = new ProposalSubmission();
        proposal1.setJobPostingId(jobPostingId.intValue());
        proposal1.setProposedBidAmount(100);

        ProposalSubmission proposal2 = new ProposalSubmission();
        proposal2.setJobPostingId(jobPostingId.intValue());
        proposal2.setProposedBidAmount(200);

        List<ProposalSubmission> proposals = List.of(proposal1, proposal2);

        when(jobPostingRepository.findById(jobPostingId)).thenReturn(Optional.of(jobPosting));
        when(proposalRepository.findByJobPostingId(jobPostingId)).thenReturn(proposals);

        JobPostingReviewDTO result = adminReviewService.getJobPostingWithProposals(jobPostingId);

        assertNotNull(result);
        assertNotNull(result.getJobPosting());
        assertEquals(jobPostingId, result.getJobPosting().getJobPostingId());
        assertEquals("Software Engineer", result.getJobPosting().getTitle());

        assertEquals(2, result.getProposals().size());
        assertEquals(100, result.getProposals().get(0).getProposedBidAmount());
        assertEquals(200, result.getProposals().get(1).getProposedBidAmount());

        verify(jobPostingRepository, times(1)).findById(jobPostingId);
        verify(proposalRepository, times(1)).findByJobPostingId(jobPostingId);
    }

    @Test
    @DisplayName("Should throw JobPostingNotFoundException if job posting not found")
    void getJobPostingWithProposals_NotFound() {
        Long jobPostingId = 99L;
        when(jobPostingRepository.findById(jobPostingId)).thenReturn(Optional.empty());

        assertThrows(JobPostingNotFoundException.class,
                () -> adminReviewService.getJobPostingWithProposals(jobPostingId));

        verify(jobPostingRepository, times(1)).findById(jobPostingId);
        verify(proposalRepository, never()).findByJobPostingId(any());
    }
}
