package com.phamtra.identity_service.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTORequest {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate dob;
}
