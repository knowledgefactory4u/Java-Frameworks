package com.knf.dev.demo.springvaadincrud.backend.repository;

import com.knf.dev.demo.springvaadincrud.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmailStartsWithIgnoreCase(String email);
}