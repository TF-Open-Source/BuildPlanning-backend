package com.acme.backendbuildplanning.infraestructure.user_management.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "El nombre no puede estar vacío")
    private String firstName;

    @NotNull(message = "El apellido no puede estar vacío")
    private String lastName;

    @NotNull(message = "El DNI no puede estar vacío")
    @Pattern(regexp = "^\\d{8}$", message = "El DNI debe tener exactamente 8 dígitos")
    private String dni;

    @NotNull(message = "El celular no puede estar vacío")
    @Pattern(regexp = "^\\d{9}$", message = "El celular debe tener exactamente 9 dígitos")
    private String phone;

    @NotNull(message = "El sexo es obligatorio")
    @Pattern(regexp = "^(M|F)$", message = "El sexo debe ser 'M' (masculino) o 'F' (femenino)")
    private String gender;
}
