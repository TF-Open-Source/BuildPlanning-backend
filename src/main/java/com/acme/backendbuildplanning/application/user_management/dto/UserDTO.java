package com.acme.backendbuildplanning.application.user_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@Getter
@Setter
@Data
public class UserDTO {
    private Long id;

    @Email(message = "Correo debe tener un formato válido")
    @NotNull(message = "El correo es obligatorio")
    private String email;

    @NotNull(message = "El tipo de usuario es obligatorio")
    private String userType;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotNull(message = "El nombre es obligatorio")
    private String firstName;

    @NotNull(message = "El apellido es obligatorio")
    private String lastName;

    @NotNull(message = "El DNI es obligatorio")
    @Pattern(regexp = "^\\d{8}$", message = "El DNI debe tener exactamente 8 dígitos")
    private String dni;

    @NotNull(message = "El celular es obligatorio")
    @Pattern(regexp = "^\\d{9}$", message = "El celular debe tener exactamente 9 dígitos")
    private String phone;

    @NotNull(message = "El sexo es obligatorio")
    @Pattern(regexp = "^(M|F)$", message = "El sexo debe ser 'M' (masculino) o 'F' (femenino)")
    private String gender;
}
