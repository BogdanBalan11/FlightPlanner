package com.example.flightplanner.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.flightplanner.Repository.AuthRepository

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    val registrationSuccess: LiveData<Boolean?> get() = authRepository.registrationSuccess
    val loginSuccess: LiveData<Boolean?> get() = authRepository.loginSuccess

    fun register(email: String, password: String, username: String) {
        authRepository.registerUser(email, password, username)
    }

    fun login(email: String, password: String) {
        authRepository.loginUser(email, password)
    }

    fun logout() {
        authRepository.logoutUser()
    }

    fun resetRegistrationStatus() {
        authRepository.resetRegistrationSuccess()
    }

    fun resetLoginStatus() {
        authRepository.resetLoginSuccess()
    }
}
