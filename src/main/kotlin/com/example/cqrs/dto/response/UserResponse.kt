package com.example.cqrs.dto.response

import com.example.cqrs.domain.User

data class UserResponse(
    val userId : Long,
    val userName : String
)

fun User.toResponse() : UserResponse {
    return UserResponse(
        userId = id,
        userName = name
    )
}
