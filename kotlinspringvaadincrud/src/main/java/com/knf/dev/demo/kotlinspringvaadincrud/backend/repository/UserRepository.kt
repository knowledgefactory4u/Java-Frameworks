package com.knf.dev.demo.kotlinspringvaadincrud.backend.repository

import com.knf.dev.demo.kotlinspringvaadincrud.backend.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User?, Long?> {
    fun findByEmailStartsWithIgnoreCase(email: String?): List<User?>?
}