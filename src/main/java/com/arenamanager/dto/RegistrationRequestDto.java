package com.arenamanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequestDto {

    @NotBlank
    @Size(max = 60)
    private String username;

    @NotBlank
    @Email
    @Size(max = 120)
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NotBlank
    @Size(min = 6, max = 100)
    private String confirmPassword;

    @Min(100)
    @Max(32000)
    private Integer preferredPeripheralDpi;

    @Size(max = 60)
    private String mouseGripStyle;

    @Size(max = 1000)
    private String bio;
}
