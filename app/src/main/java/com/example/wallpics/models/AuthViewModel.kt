package com.example.wallpics.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.wallpics.data.DatabaseProvider
import com.example.wallpics.data.User
import com.example.wallpics.data.repo.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository

    init {
        val userDao = DatabaseProvider.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }

    var isAuthenticated = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    val isRegistrationSuccessful = MutableStateFlow(false)

    fun clearErrorMessage() {
        errorMessage.value = ""
    }


    fun authenticate(username: String, password: String) {
        viewModelScope.launch {
            errorMessage.value = ""
            if (username.isBlank() || password.isBlank()) {
                errorMessage.value = "Usuario y Contraseña no pueden estar vacios."
                return@launch
            }
            val user = userRepository.authenticateUser(username, password)
            if (user != null) {
                isAuthenticated.value = true
                errorMessage.value = ""
            } else {
                isAuthenticated.value = false
                errorMessage.value = "Credenciales Inválidas. Intenta Nuevamente."
            }
        }
    }

    fun registerUser(username: String, password: String) {
        viewModelScope.launch {
            if (username.isBlank() || password.isBlank()) {
                errorMessage.value = "Usuario y Contraseña no pueden estar vacíos."
                isRegistrationSuccessful.value = false
                return@launch
            }

            try {
                userRepository.registerUser(User(username = username, password = password))
                isRegistrationSuccessful.value = true
                errorMessage.value = "" // Limpia el mensaje de error
            } catch (e: Exception) {
                errorMessage.value = "Error al registrar usuario: ${e.message}"
                isRegistrationSuccessful.value = false
            }
        }
    }

    fun logout() {
        isAuthenticated.value = false
        clearErrorMessage()
    }
}
