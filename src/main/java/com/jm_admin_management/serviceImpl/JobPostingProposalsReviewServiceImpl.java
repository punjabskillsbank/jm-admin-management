package com.jm_admin_management.serviceImpl;

import com.common.dto.JobPostingDTO;
import com.common.dto.ProposalSubmissionDTO;
import com.common.entity.JobPosting;
import com.common.entity.ProposalSubmission;
import com.common.exceptionHandling.JobPostingNotFoundException;
import com.jm_admin_management.dto.JobPostingProposalsReviewDTO;
import com.jm_admin_management.repository.JobPostingRepository;
import com.jm_admin_management.repository.ProposalRepository;
import com.jm_admin_management.service.JobPostingProposalsReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostingProposalsReviewServiceImpl implements JobPostingProposalsReviewService {

    private final ModelMapper modelMapper;
    private final JobPostingRepository jobPostingRepository;
    private final ProposalRepository proposalRepository;

    @Override
    public JobPostingProposalsReviewDTO getJobPostingWithProposals(Long jobPostingId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new JobPostingNotFoundException(jobPostingId));

        List<ProposalSubmission> proposals = proposalRepository.findByJobPostingId(jobPostingId);
        JobPostingDTO jobPostingDTO = modelMapper.map(jobPosting, JobPostingDTO.class);

        List<ProposalSubmissionDTO> proposalDTOs = proposals.stream()
                .map(p -> modelMapper.map(p, ProposalSubmissionDTO.class))
                .toList();
        JobPostingProposalsReviewDTO reviewDTO = modelMapper.map(jobPostingDTO, JobPostingProposalsReviewDTO.class);
        reviewDTO.setProposals(proposalDTOs);
        return reviewDTO;

    }
}
