package com.library.libraryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.libraryapi.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Opcional: buscar usuario por email
    User findByEmail(String email);
}