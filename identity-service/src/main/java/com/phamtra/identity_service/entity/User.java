package com.phamtra.identity_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "")
public class User {

    private long id;
    private String username;
    private String password;

}
