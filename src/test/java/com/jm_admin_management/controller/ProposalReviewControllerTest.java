package com.jm_admin_management.controller;


import com.common.dto.ProposalSubmissionDTO;
import com.common.enums.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jm_admin_management.dto.JobPostingReviewDTO;
import com.jm_admin_management.service.JobProposalReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProposalsReviewController.class)
class ProposalReviewControllerTest {

    @MockitoBean
    private JobProposalReviewService jobProposalReviewService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/admin_management/proposals_review/{jobPostingId} should return JobPostingReviewDTO with jobPosting and proposals")
    void testGetJobPostingWithProposals() throws Exception {
        Long jobPostingId = 1L;

        ProposalSubmissionDTO proposal1 = ProposalSubmissionDTO.builder()
                .jobPostingId(jobPostingId)
                .freelancerId(UUID.randomUUID())
                .clientId(UUID.randomUUID())
                .proposedBidAmount(1000)
                .proposalStatus(ProposalStatus.SUBMITTED) // Use your actual enum values
                .coverLetter("I have strong experience with Java.")
                .build();

        ProposalSubmissionDTO proposal2 = ProposalSubmissionDTO.builder()
                .jobPostingId(jobPostingId)
                .freelancerId(UUID.randomUUID())
                .clientId(UUID.randomUUID())
                .proposedBidAmount(1200)
                .proposalStatus(ProposalStatus.SUBMITTED)
                .coverLetter("I am very interested in this project.")
                .build();

        JobPostingReviewDTO reviewDTO = new JobPostingReviewDTO();
        reviewDTO.setProposals(List.of(proposal1, proposal2));

        when(jobProposalReviewService.getJobPostingWithProposals(jobPostingId)).thenReturn(reviewDTO);

        mockMvc.perform(get("/api/admin_management/proposals_review/{jobPostingId}", jobPostingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.proposals", hasSize(2)))
                .andDo(MockMvcResultHandlers.print());

        verify(jobProposalReviewService, times(1)).getJobPostingWithProposals(jobPostingId);
    }

    @Test
    @DisplayName("GET /api/admin_management/proposals_review/{jobPostingId} should return empty proposals list when no proposals exist")
    void testGetJobPostingWithProposals_EmptyList() throws Exception
    {
        Long jobPostingId = 1L;
        JobPostingReviewDTO reviewDTO = new JobPostingReviewDTO();
        reviewDTO.setProposals(List.of());

        when(jobProposalReviewService.getJobPostingWithProposals(jobPostingId)).thenReturn(reviewDTO);

        mockMvc.perform(get("/api/admin_management/proposals_review/{jobPostingId}", jobPostingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.proposals", hasSize(0)))
                .andDo(MockMvcResultHandlers.print());

        verify(jobProposalReviewService, times(1)).getJobPostingWithProposals(jobPostingId);
    }
}
