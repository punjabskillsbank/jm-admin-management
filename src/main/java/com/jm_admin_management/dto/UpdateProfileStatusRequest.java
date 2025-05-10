package com.jm_admin_management.dto;

import com.common.enums.ProfileStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProfileStatusRequest {

    @NotNull(message = "Profile status cannot be null")
    private ProfileStatus profileStatus;
}
