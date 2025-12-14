package com.phamtra.identity_service.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginDTO {

    private String username;
    private String password;

}
