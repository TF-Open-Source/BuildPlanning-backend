package com.acme.backendbuildplanning.application.user_management.dto.user_services;

import com.acme.backendbuildplanning.application.user_management.dto.UserDTO;
import com.acme.backendbuildplanning.domain.user_management.model.User;
import com.acme.backendbuildplanning.domain.user_management.model.UserType;
import com.acme.backendbuildplanning.domain.user_management.model.repository.UserRepository;
import com.acme.backendbuildplanning.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO registerUser(UserDTO userDTO) {
        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(userDTO.getPassword());
        try {
            newUser.setUserType(UserType.valueOf(userDTO.getUserType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de usuario inválido.");
        }

        User savedUser = userRepository.save(newUser);
        return mapToDTO(savedUser);
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

        // Retornar un token ficticio o real generado
        return "token_generado";
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