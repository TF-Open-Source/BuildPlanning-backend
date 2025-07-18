package com.acme.backendbuildplanning.infraestructure.user_management.rest;

import com.acme.backendbuildplanning.application.user_management.dto.UserDTO;
import com.acme.backendbuildplanning.application.user_management.dto.user_services.UserService;
import com.acme.backendbuildplanning.infraestructure.user_management.rest.dto.AuthResponse;
import com.acme.backendbuildplanning.infraestructure.user_management.rest.dto.LoginRequest;
import com.acme.backendbuildplanning.infraestructure.user_management.rest.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UserService userService;

    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody RegisterRequest request) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(request.getEmail());
        userDTO.setPassword(request.getPassword());
        userDTO.setUserType(request.getUserType());
        userDTO.setFirstName(request.getFirstName());
        userDTO.setLastName(request.getLastName());
        userDTO.setDni(request.getDni());
        userDTO.setPhone(request.getPhone());
        userDTO.setGender(request.getGender());

        UserDTO savedUser = userService.registerUser(userDTO);

        if (savedUser == null || savedUser.getId() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al registrar el usuario"));
        }
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "User registrado con éxito");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            String token = userService.login(request.getEmail(), request.getPassword());
            UserDTO userDTO = userService.getUserByEmail(request.getEmail());

            AuthResponse response = new AuthResponse();
            response.setToken(token);
            response.setUserType(userDTO.getUserType());
            response.setUserId(userDTO.getId());

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Contraseña incorrecta")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            } else if (e.getMessage().equals("User no encontrado con ese correo")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    }

}