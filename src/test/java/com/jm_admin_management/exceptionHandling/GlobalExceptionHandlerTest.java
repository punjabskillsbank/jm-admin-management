package com.jm_admin_management.exceptionHandling;

import com.jm_admin_management.controller.FreelancerProfileController;
import com.jm_admin_management.service.FreelancerProfileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FreelancerProfileController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FreelancerProfileService freelancerProfileService;

    @Test
    void shouldReturnNoPendingFreelancerException_whenNoPendingFreelancer() throws Exception {

        Mockito.when(freelancerProfileService.getPendingFreelancers())
                .thenThrow(new NoPendingFreelancerException());

        // Perform a GET request to the endpoint that triggers the exception
        mockMvc.perform(MockMvcRequestBuilders.get("/api/freelancers/getPendingFreelancers"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No pending freelancer found"));
    }

}