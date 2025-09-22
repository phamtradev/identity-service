package com.phamtra.identity_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateDTORequest {
    
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate dob;
}
