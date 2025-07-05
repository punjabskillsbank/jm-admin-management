package com.jm_admin_management.exceptionHandling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    @DisplayName("handleFreelancerNotFound should return NOT_FOUND status and the correct message")
    void handleFreelancerNotFound_ShouldReturnNotFoundAndCorrectMessage() {
        // Arrange
        UUID freelancerId = UUID.randomUUID();
        FreelancerNotFoundException exception = new FreelancerNotFoundException(freelancerId);

        // Act
        ResponseEntity<String> response = globalExceptionHandler.handleFreelancerNotFound(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Freelancer with ID " + freelancerId + " not found", response.getBody());
    }
}