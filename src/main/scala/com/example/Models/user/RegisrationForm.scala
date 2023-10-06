package com.example.Models.user

case class UserRegistrationForm(
    name: String,
    email: String,
    password: String,
    passwordConfirmation: String
)
