package com.example.ordernow.presentation.sign_in

data class SignInState(
    val isSignInSuccessful : Boolean = false,
    val signInError : String? = null,
    val isLoading: Boolean = false
)
