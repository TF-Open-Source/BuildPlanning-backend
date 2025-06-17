package com.acme.backendbuildplanning.infraestructure.user_management.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String password;
    private String email;
}