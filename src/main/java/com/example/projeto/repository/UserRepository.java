package com.example.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projeto.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    
}
