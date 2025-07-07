package com.acme.backendbuildplanning.application.user_management.dto.user_services;

import com.acme.backendbuildplanning.application.user_management.dto.UserDTO;
import com.acme.backendbuildplanning.domain.user_management.model.User;
import com.acme.backendbuildplanning.domain.user_management.model.UserType;
import com.acme.backendbuildplanning.domain.user_management.model.repository.UserRepository;
import com.acme.backendbuildplanning.exception.NotFoundException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public UserDTO registerUser(@Valid UserDTO userDTO) {
        try{
            User newUser = new User();
            System.out.println("Email = " + userDTO.getEmail());
            newUser.setEmail(userDTO.getEmail());
            newUser.setPassword(userDTO.getPassword());
            try {
                newUser.setUserType(UserType.valueOf(userDTO.getUserType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Tipo de usuario inválido.");
            }
            User savedUser = userRepository.save(newUser);
            return mapToDTO(savedUser);
        } catch (IllegalArgumentException e){

            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User no encontrado"));
        return mapToDTO(user);
    }

    // Iniciar sesión con correo y contraseña
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User no encontrado con ese correo"));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
        String jwt = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("userType", user.getUserType().name())
                .setIssuedAt(new Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 3600_000)) // 1 hora
                .signWith(SECRET_KEY)
                .compact();


        // Retornar un token ficticio o real generado
        return jwt;
    }


    // Conversión de entidad a DTO
    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUserType(user.getUserType().name());
        return dto;
    }
}