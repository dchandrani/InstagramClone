package com.dh.instagramclone.ui.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel @ViewModelInject constructor() : ViewModel() {
    private val registerEventChannel = Channel<RegisterEvent>()
    val registerEvent = registerEventChannel.receiveAsFlow()

    fun registerClicked(email: String, password: String, confirmPassword: String) {
        if (email.isBlank()) {
            println("invalid email")
            sendInvalidInputMessage("Email cannot be empty")
            return
        }

        if (password.isBlank()) {
            sendInvalidInputMessage("Password cannot be empty")
            return
        }

        if (confirmPassword.isBlank()) {
            sendInvalidInputMessage("Confirm Password cannot be empty")
            return
        }

        if (password != confirmPassword) {
            sendInvalidInputMessage("Password do not match")
            return
        }

        registerUser(email, password)
    }

    private fun registerUser(email: String, password: String) = viewModelScope.launch {
        registerEventChannel.send(RegisterViewModel.RegisterEvent.RegistrationSuccessful)
    }

    private fun sendInvalidInputMessage(text: String) = viewModelScope.launch {
        registerEventChannel.send(RegisterViewModel.RegisterEvent.ShowInvalidInputMessage(text))
    }


    sealed class RegisterEvent {
        data class ShowInvalidInputMessage(val message: String) : RegisterEvent()
        object RegistrationSuccessful : RegisterEvent()
        data class RegistrationFailed(val message: String) : RegisterEvent()
    }
}