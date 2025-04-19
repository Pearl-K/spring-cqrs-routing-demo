package com.example.cqrs.controller

import com.example.cqrs.domain.User
import com.example.cqrs.dto.request.CreateUserRequest
import com.example.cqrs.dto.response.UserResponse
import com.example.cqrs.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getAllUsers() : ResponseEntity<List<UserResponse>> {
        val users = userService.findAllUsers()
        return ResponseEntity.ok(users)
    }

    @PostMapping
    fun createUser(
        @RequestBody request : CreateUserRequest
    ) : ResponseEntity<User> {
        val savedUser = userService.createUser(request)
        return ResponseEntity.ok(savedUser)
    }
}