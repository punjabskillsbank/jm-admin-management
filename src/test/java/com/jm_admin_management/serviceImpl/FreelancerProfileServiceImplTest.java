package com.jm_admin_management.serviceImpl;

import com.common.entity.Freelancer;
import com.common.enums.ProfileStatus;
import com.jm_admin_management.repository.FreelancerRepository;
import com.jm_admin_management.test_utils.factory.FreelancerTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FreelancerProfileServiceImplTest {

    @Mock
    private FreelancerRepository freelancerRepository;

    @InjectMocks
    private FreelancerProfileServiceImpl freelancerProfileService;

    private List<Freelancer> pendingFreelancers;

    @BeforeEach
    void setUp() {
        // Initialize test data using the factory
        pendingFreelancers = FreelancerTestDataFactory.createFreelancerList(5, ProfileStatus.PENDING);
    }

    @Test
    @DisplayName("Should return list of pending freelancers when there are pending freelancers")
    void getPendingFreelancers_WithPendingFreelancers_ReturnsFreelancerList() {
        // Arrange
        when(freelancerRepository.getFreelancersByProfileStatus(ProfileStatus.PENDING))
                .thenReturn(pendingFreelancers);

        // Act
        List<Freelancer> result = freelancerProfileService.getPendingFreelancers();

        // Assert
        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals(ProfileStatus.PENDING, result.get(0).getProfileStatus());
        assertEquals(ProfileStatus.PENDING, result.get(1).getProfileStatus());
        assertEquals("Java Developer", result.get(0).getTitle());
        assertEquals("Full Stack Developer", result.get(1).getTitle());
        verify(freelancerRepository, times(1)).getFreelancersByProfileStatus(ProfileStatus.PENDING);
    }

    @Test
    @DisplayName("Should return empty list when there are no pending freelancers")
    void getPendingFreelancers_WithNoPendingFreelancers_ReturnsEmptyList() {
        // Arrange
        when(freelancerRepository.getFreelancersByProfileStatus(ProfileStatus.PENDING))
                .thenReturn(new ArrayList<>());

        // Act & Assert
        List<Freelancer> result = freelancerProfileService.getPendingFreelancers();
        assertEquals(Collections.emptyList(), result);

        verify(freelancerRepository, times(1)).getFreelancersByProfileStatus(ProfileStatus.PENDING);
    }


    @Test
    @DisplayName("Should return freelancers with complete entity relationships")
    void getPendingFreelancers_ShouldReturnCompleteEntities() {
        // Arrange
        Freelancer freelancerWithRelations = FreelancerTestDataFactory.createFreelancerWithRelations(
                0, ProfileStatus.PENDING, 2, 3, 1);

        when(freelancerRepository.getFreelancersByProfileStatus(ProfileStatus.PENDING))
                .thenReturn(List.of(freelancerWithRelations));

        // Act
        List<Freelancer> result = freelancerProfileService.getPendingFreelancers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        Freelancer returnedFreelancer = result.get(0);
        assertEquals(2, returnedFreelancer.getEducations().size());
        assertEquals(3, returnedFreelancer.getJobs().size());
        assertEquals(1, returnedFreelancer.getCertificates().size());
        verify(freelancerRepository, times(1)).getFreelancersByProfileStatus(ProfileStatus.PENDING);
    }

    @Test
    @DisplayName("Should return only pending freelancers if freelancers with other statuses exist")
    void getPendingFreelancers_ShouldReturnOnlyPendingFreelancers() {
        // Arrange
        List<Freelancer> freelancers = new ArrayList<>();
        freelancers.add(FreelancerTestDataFactory.createFreelancer(1, ProfileStatus.PENDING));
        freelancers.add(FreelancerTestDataFactory.createFreelancer(2, ProfileStatus.APPROVED));
        freelancers.add(FreelancerTestDataFactory.createFreelancer(3, ProfileStatus.REJECTED));

        when(freelancerRepository.getFreelancersByProfileStatus(ProfileStatus.PENDING))
                .thenReturn(Collections.singletonList(freelancers.get(0)));

        // Act
        List<Freelancer> result = freelancerProfileService.getPendingFreelancers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ProfileStatus.PENDING, result.get(0).getProfileStatus());
    }
}