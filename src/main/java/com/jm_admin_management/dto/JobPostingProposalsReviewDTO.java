package com.jm_admin_management.dto;


import com.common.dto.JobPostingDTO;
import com.common.dto.ProposalSubmissionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPostingProposalsReviewDTO {

    private JobPostingDTO jobPosting;
    private List<ProposalSubmissionDTO> proposals;
}
