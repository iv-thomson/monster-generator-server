package com.example.Models.user

import java.util.UUID

case class LoginRequest(username: String, password: String)

case class User(
    name: String,
    hash: String,
    id: String = UUID.randomUUID().toString()
)
