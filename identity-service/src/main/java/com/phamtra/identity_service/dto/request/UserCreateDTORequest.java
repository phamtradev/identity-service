package com.phamtra.identity_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserCreateDTORequest {

    @Size(min = 3, message = "Username phải có chứa nhất 3 ký tự !")
    private String username;

    @Size(min = 8, message = "Password phải chứa ít nhất 8 ký tự !")
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate dob;
}
