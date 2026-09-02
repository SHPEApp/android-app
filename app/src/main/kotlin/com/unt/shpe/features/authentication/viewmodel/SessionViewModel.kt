package com.unt.shpe.features.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unt.shpe.features.authentication.model.DemoCredentials
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SessionViewModel(
    private val authenticationDelay: Long = 350
) : ViewModel() {
    enum class State {
        SIGNED_OUT,
        SIGNING_IN,
        SIGNING_OUT,
        SIGNED_IN,
        FAILED,
    }

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _state = MutableStateFlow(State.SIGNED_OUT)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val isSignedIn: Boolean
        get() = _state.value == State.SIGNED_IN

    val isLoading: Boolean
        get() = _state.value == State.SIGNING_IN || _state.value == State.SIGNING_OUT

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun signIn() {
        viewModelScope.launch {
            val trimmedEmail = _email.value.trim()
            
            if (trimmedEmail.isEmpty()) {
                _errorMessage.value = "Enter your email address."
                _state.value = State.FAILED
                return@launch
            }
            
            if (_password.value.length < 6) {
                _errorMessage.value = "Enter a password with at least 6 characters."
                _state.value = State.FAILED
                return@launch
            }
            
            if (trimmedEmail.equals(DemoCredentials.workingEmail, ignoreCase = true) &&
                _password.value == DemoCredentials.workingPassword) {
                _state.value = State.SIGNING_IN
                _errorMessage.value = null
                
                delay(authenticationDelay)
                
                _state.value = State.SIGNED_IN
            } else {
                _errorMessage.value = "Invalid demo credentials."
                _state.value = State.FAILED
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _state.value = State.SIGNING_OUT
            _errorMessage.value = null
            
            delay(authenticationDelay)
            
            _email.value = ""
            _password.value = ""
            _state.value = State.SIGNED_OUT
        }
    }
}
