package com.jm_admin_management.test_utils.factory;

import com.common.dto.CertificateDTO;
import com.common.dto.EducationDTO;
import com.common.dto.FreelancerDTO;
import com.common.dto.JobDTO;
import com.common.entity.Certificate;
import com.common.entity.Education;
import com.common.entity.Freelancer;
import com.common.entity.Job;
import com.common.enums.ProfileStatus;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Factory class to create test Freelancer entities and DTOs for unit testing
 */
public class FreelancerTestDataFactory {

    // Standard data for test freelancers
    private static final String[] TITLES = {
            "Java Developer",
            "Full Stack Developer",
            "UX/UI Designer",
            "DevOps Engineer",
            "Data Scientist"
    };

    private static final String[] BIOS = {
            "Experienced Java developer with 8+ years in enterprise applications.",
            "Full stack developer specializing in React and Spring Boot.",
            "UX/UI designer creating intuitive and engaging interfaces.",
            "DevOps engineer with expertise in AWS and Kubernetes.",
            "Data scientist with background in machine learning and statistical analysis."
    };

    private static final int[] HOURLY_RATES = { 75, 85, 65, 90, 95 };

    private static final String[] ADDRESSES = {
            "123 Tech Street",
            "456 Developer Avenue",
            "789 Design Boulevard",
            "321 Cloud Road",
            "654 Data Lane"
    };

    private static final String[] CITIES = {
            "Bangalore",
            "Mumbai",
            "Delhi",
            "Hyderabad",
            "Chennai"
    };

    private static final String[] STATES = {
            "Karnataka",
            "Maharashtra",
            "Delhi",
            "Telangana",
            "Tamil Nadu"
    };

    private static final String COUNTRY = "India";

    private static final String[] POSTAL_CODES = {
            "560001",
            "400001",
            "110001",
            "500001",
            "600001"
    };

    private static final String[] PHONE_NUMBERS = {
            "+917012345678",
            "+918987654321",
            "+919876543210",
            "+916789012345",
            "+919012345678"
    };

    private static final boolean[] ABC_MEMBERSHIPS = {true, false, true, false, true};

    private static final String[] PROFILE_PHOTOS = {
            "https://example.com/photos/profile1.jpg",
            "https://example.com/photos/profile2.jpg",
            "https://example.com/photos/profile3.jpg",
            "https://example.com/photos/profile4.jpg",
            "https://example.com/photos/profile5.jpg"
    };

    /**
     * Creates a single Freelancer entity with predefined values
     * @param index Index for selecting predefined test data (0-4)
     * @param status ProfileStatus to assign
     * @return Freelancer entity
     */
    public static Freelancer createFreelancer(int index, ProfileStatus status) {
        index = Math.min(Math.max(index, 0), 4); // Ensure index is between 0-4

        Freelancer freelancer = new Freelancer();
        freelancer.setFreelancerId(UUID.randomUUID());
        freelancer.setTitle(TITLES[index]);
        freelancer.setBio(BIOS[index]);
        freelancer.setHourlyRate(HOURLY_RATES[index]);
        freelancer.setAddress(ADDRESSES[index]);
        freelancer.setCity(CITIES[index]);
        freelancer.setState(STATES[index]);
        freelancer.setCountry(COUNTRY);
        freelancer.setPostalCode(POSTAL_CODES[index]);
        freelancer.setPhoneNumber(PHONE_NUMBERS[index]);
        freelancer.setAbcMember(ABC_MEMBERSHIPS[index]);
        freelancer.setProfilePhotoURL(PROFILE_PHOTOS[index]);
        freelancer.setProfileStatus(status);
        freelancer.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        freelancer.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        // Initialize empty collections
        freelancer.setEducations(new ArrayList<>());
        freelancer.setJobs(new ArrayList<>());
        freelancer.setCertificates(new ArrayList<>());

        return freelancer;
    }

    /**
     * Creates a single FreelancerDTO with predefined values
     * @param index Index for selecting predefined test data (0-4)
     * @param status ProfileStatus to assign
     * @return FreelancerDTO
     */
    public static FreelancerDTO createFreelancerDTO(int index, ProfileStatus status) {
        index = Math.min(Math.max(index, 0), 4); // Ensure index is between 0-4

        return FreelancerDTO.builder()
                .freelancerId(UUID.randomUUID())
                .title(TITLES[index])
                .bio(BIOS[index])
                .hourlyRate(HOURLY_RATES[index])
                .address(ADDRESSES[index])
                .city(CITIES[index])
                .state(STATES[index])
                .country(COUNTRY)
                .postalCode(POSTAL_CODES[index])
                .phoneNumber(PHONE_NUMBERS[index])
                .isAbcMember(ABC_MEMBERSHIPS[index])
                .profilePhotoURL(PROFILE_PHOTOS[index])
                .profileStatus(status)
                .educations(new ArrayList<>())
                .jobs(new ArrayList<>())
                .certificates(new ArrayList<>())
                .build();
    }

    /**
     * Creates a list of Freelancer entities with predefined values and specified status
     * @param count Number of freelancers to create
     * @param status ProfileStatus to assign to all freelancers
     * @return List of Freelancer entities
     */
    public static List<Freelancer> createFreelancerList(int count, ProfileStatus status) {
        return IntStream.range(0, count)
                .mapToObj(i -> createFreelancer(i % 5, status))
                .collect(Collectors.toList());
    }

    /**
     * Creates a list of FreelancerDTOs with predefined values and specified status
     * @param count Number of freelancer DTOs to create
     * @param status ProfileStatus to assign to all freelancer DTOs
     * @return List of FreelancerDTOs
     */
    public static List<FreelancerDTO> createFreelancerDTOList(int count, ProfileStatus status) {
        return IntStream.range(0, count)
                .mapToObj(i -> createFreelancerDTO(i % 5, status))
                .collect(Collectors.toList());
    }

    /**
     * Creates a Freelancer entity with additional relationship entities
     * @param index Index for selecting predefined test data (0-4)
     * @param status ProfileStatus to assign
     * @param educationCount Number of education records to create
     * @param jobCount Number of job records to create
     * @param certificateCount Number of certificate records to create
     * @return Freelancer entity with populated relationships
     */
    public static Freelancer createFreelancerWithRelations(
            int index,
            ProfileStatus status,
            int educationCount,
            int jobCount,
            int certificateCount) {

        Freelancer freelancer = createFreelancer(index, status);

        // Add education records
        List<Education> educations = new ArrayList<>();
        for (int i = 0; i < educationCount; i++) {
            Education education = new Education();
            education.setFreelancer(freelancer);
            // Add additional fields as needed
            educations.add(education);
        }
        freelancer.setEducations(educations);

        // Add job records
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < jobCount; i++) {
            Job job = new Job();
            job.setFreelancer(freelancer);
            // Add additional fields as needed
            jobs.add(job);
        }
        freelancer.setJobs(jobs);

        // Add certificate records
        List<Certificate> certificates = new ArrayList<>();
        for (int i = 0; i < certificateCount; i++) {
            Certificate certificate = new Certificate();
            certificate.setFreelancer(freelancer);
            // Add additional fields as needed
            certificates.add(certificate);
        }
        freelancer.setCertificates(certificates);

        return freelancer;
    }

    /**
     * Creates a FreelancerDTO with additional relationship DTOs
     * @param index Index for selecting predefined test data (0-4)
     * @param status ProfileStatus to assign
     * @param educationCount Number of education DTOs to create
     * @param jobCount Number of job DTOs to create
     * @param certificateCount Number of certificate DTOs to create
     * @return FreelancerDTO with populated relationships
     */
    public static FreelancerDTO createFreelancerDTOWithRelations(
            int index,
            ProfileStatus status,
            int educationCount,
            int jobCount,
            int certificateCount) {

        FreelancerDTO freelancerDTO = createFreelancerDTO(index, status);

        // Add education DTOs
        List<EducationDTO> educationDTOs = new ArrayList<>();
        for (int i = 0; i < educationCount; i++) {
            EducationDTO educationDTO = new EducationDTO();
            // Add additional fields as needed
            educationDTOs.add(educationDTO);
        }
        freelancerDTO.setEducations(educationDTOs);

        // Add job DTOs
        List<JobDTO> jobDTOs = new ArrayList<>();
        for (int i = 0; i < jobCount; i++) {
            JobDTO jobDTO = new JobDTO();
            // Add additional fields as needed
            jobDTOs.add(jobDTO);
        }
        freelancerDTO.setJobs(jobDTOs);

        // Add certificate DTOs
        List<CertificateDTO> certificateDTOs = new ArrayList<>();
        for (int i = 0; i < certificateCount; i++) {
            CertificateDTO certificateDTO = new CertificateDTO();
            // Add additional fields as needed
            certificateDTOs.add(certificateDTO);
        }
        freelancerDTO.setCertificates(certificateDTOs);

        return freelancerDTO;
    }

    /**
     * Converts a Freelancer entity to a FreelancerDTO (simple conversion without relationships)
     * @param freelancer Freelancer entity
     * @return FreelancerDTO
     */
    public static FreelancerDTO convertToDTO(Freelancer freelancer) {
        return FreelancerDTO.builder()
                .freelancerId(freelancer.getFreelancerId())
                .title(freelancer.getTitle())
                .bio(freelancer.getBio())
                .hourlyRate(freelancer.getHourlyRate())
                .address(freelancer.getAddress())
                .city(freelancer.getCity())
                .state(freelancer.getState())
                .country(freelancer.getCountry())
                .postalCode(freelancer.getPostalCode())
                .phoneNumber(freelancer.getPhoneNumber())
                .isAbcMember(freelancer.isAbcMember())
                .profilePhotoURL(freelancer.getProfilePhotoURL())
                .profileStatus(freelancer.getProfileStatus())
                .build();
    }
}