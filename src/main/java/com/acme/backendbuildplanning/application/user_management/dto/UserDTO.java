package com.acme.backendbuildplanning.application.user_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private String password;  // Asegúrate de gestionar la contraseña de forma segura
}
