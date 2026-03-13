package com.library.libraryapi.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.library.libraryapi.models.User;
import com.library.libraryapi.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Crear usuario
    public User createUser(User user) {
        // Asignar fecha de registro automáticamente
        user.setFechaRegistro(LocalDate.now());
        return userRepository.save(user);
    }

    // Obtener usuario por ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Actualizar usuario
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id); // reutiliza método seguro

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setTipoUsuario(userDetails.getTipoUsuario()); // enum en vez de role

        return userRepository.save(user);
    }

    // Eliminar usuario
    public void deleteUser(Long id) {
        User user = getUserById(id); // verificar existencia
        userRepository.delete(user);
    }
}