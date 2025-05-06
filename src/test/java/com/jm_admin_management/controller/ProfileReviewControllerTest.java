package com.jm_admin_management.controller;

import com.common.entity.Freelancer;
import com.common.enums.ProfileStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jm_admin_management.dto.UpdateProfileStatusRequest;
import com.jm_admin_management.service.FreelancerProfileService;
import com.jm_admin_management.test_utils.factory.FreelancerTestDataFactory;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileReviewController.class)
class ProfileReviewControllerTest {

    @MockitoBean
    private FreelancerProfileService freelancerProfileService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/admin_management/pending_freelancers should return list of pending freelancers")
    void getPendingFreelancers_ShouldReturnFreelancersList() throws Exception {
        // Arrange
        List<Freelancer> pendingFreelancers = FreelancerTestDataFactory.createFreelancerList(3, ProfileStatus.PENDING);
        when(freelancerProfileService.getPendingFreelancers()).thenReturn(pendingFreelancers);

        // Act & Assert
        mockMvc.perform(get("/api/admin_management/pending_freelancers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Java Developer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].profileStatus").value("PENDING"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].hourlyRate").value(75.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Full Stack Developer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value("UX/UI Designer"))
                .andDo(MockMvcResultHandlers.print());

        verify(freelancerProfileService, times(1)).getPendingFreelancers();
    }

    @Test
    @DisplayName("GET /api/admin_management/pending_freelancers should return empty list when no pending freelancers")
    void getPendingFreelancers_WithEmptyResult_ShouldReturnEmptyList() throws Exception {
        // Arrange
        when(freelancerProfileService.getPendingFreelancers()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api/admin_management/pending_freelancers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)))
                .andDo(MockMvcResultHandlers.print());

        verify(freelancerProfileService, times(1)).getPendingFreelancers();
    }

    @Test
    @DisplayName("PATCH /api/admin_management/freelancers/{freelancerId}/status should update freelancer status successfully")
    void updateProfileStatus_ShouldReturnOk() throws Exception {
        // Arrange
        UUID freelancerId = UUID.randomUUID();
        ProfileStatus newStatus = ProfileStatus.APPROVED;
        UpdateProfileStatusRequest request = new UpdateProfileStatusRequest();
        request.setProfileStatus(newStatus);
        String requestBody = objectMapper.writeValueAsString(request);

        // Act & Assert
        mockMvc.perform(patch("/api/admin_management/freelancers/{freelancerId}/update_profile_status", freelancerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Freelancer profile status updated successfully."))
                .andDo(MockMvcResultHandlers.print());

        verify(freelancerProfileService, times(1)).updateProfileStatus(freelancerId, newStatus);
    }

    @Test
    @DisplayName("PATCH /api/admin_management/freelancers/{freelancerId}/status should return bad request for invalid status")
    void updateProfileStatus_WithInvalidStatus_ShouldReturnBadRequest() throws Exception {
        // Arrange
        UUID freelancerId = UUID.randomUUID();
        UpdateProfileStatusRequest request = new UpdateProfileStatusRequest();
        request.setProfileStatus(null); // Simulate invalid status
        String requestBody = objectMapper.writeValueAsString(request);

        // Act & Assert
        mockMvc.perform(patch("/api/admin_management/freelancers/{freelancerId}/update_profile_status", freelancerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        verify(freelancerProfileService, never()).updateProfileStatus(any(), any());
    }
}