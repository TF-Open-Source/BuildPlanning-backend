package com.acme.backendbuildplanning.infraestructure.user_management.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotNull(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe tener un formato válido")
    private String email;

    @NotNull(message = "La contraseña no puede estar vacía")
    private String password;

    @NotNull(message = "El tipo de usuario no puede estar vacío")
    private String userType;




}
