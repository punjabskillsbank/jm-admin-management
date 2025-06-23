package com.jm_admin_management.serviceImpl;


import com.common.dto.JobPostingDTO;
import com.common.dto.ProposalSubmissionDTO;
import com.common.entity.JobPosting;
import com.common.entity.ProposalSubmission;
import com.jm_admin_management.dto.JobPostingReviewDTO;
import com.jm_admin_management.exceptionHandling.JobPostingNotFoundException;
import com.jm_admin_management.repository.JobPostingRepository;
import com.jm_admin_management.repository.ProposalRepository;
import com.jm_admin_management.service.AdminReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminReviewServiceImpl implements AdminReviewService {

    private final ModelMapper modelMapper;
    private final JobPostingRepository jobPostingRepository;
    private final ProposalRepository proposalRepository;

    @Override
    public JobPostingReviewDTO getJobPostingWithProposals(Long jobPostingId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new JobPostingNotFoundException(jobPostingId));

        List<ProposalSubmission> proposals = proposalRepository.findByJobPostingId(jobPostingId);
        JobPostingDTO jobPostingDTO = modelMapper.map(jobPosting, JobPostingDTO.class);

        List<ProposalSubmissionDTO> proposalDTOs = proposals.stream()
                .map(p -> modelMapper.map(p, ProposalSubmissionDTO.class))
                .toList();

        return JobPostingReviewDTO.builder()
                .jobPosting(jobPostingDTO)
                .proposals(proposalDTOs)
                .build();
    }
}
