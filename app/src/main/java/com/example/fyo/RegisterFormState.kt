package com.example.fyo

data class RegisterFormState(
    val usernameError: Int? = null,
    val rgEmailError:Int? = null,
    val passwordError: Int? = null,
    val repeatpasswordError: Int? = null,
    val isDataValid: Boolean = false
)