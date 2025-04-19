package com.example.cqrs.service

import com.example.cqrs.annotation.ReadOnly
import com.example.cqrs.domain.User
import com.example.cqrs.domain.UserRepository
import com.example.cqrs.dto.request.CreateUserRequest
import com.example.cqrs.dto.response.UserResponse
import com.example.cqrs.dto.response.toResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository
) {

    @ReadOnly
    @Transactional(readOnly = true)
    fun findAllUsers() : List<UserResponse> {
        return userRepository.findAll().map { it.toResponse() }
    }

    @Transactional
    fun createUser(request : CreateUserRequest) : User {
        return userRepository.save(User(name = request.name))
    }
}