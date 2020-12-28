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
            sendInvalidInputMessage("Email cannot be empty", InvalidInputFor.INVALID_EMAIL)
            return
        }

        if (password.isBlank()) {
            sendInvalidInputMessage("Password cannot be empty", InvalidInputFor.INVALID_PASSWORD)
            return
        }

        if (confirmPassword.isBlank()) {
            sendInvalidInputMessage(
                "Confirm Password cannot be empty",
                InvalidInputFor.INVALID_CONFIRM_PASSWORD
            )
            return
        }

        if (password != confirmPassword) {
            sendInvalidInputMessage(
                "Password do not match",
                InvalidInputFor.CONFIRM_PASSWORD_MISMATCH
            )
            return
        }

        registerUser(email, password)
    }

    private fun registerUser(email: String, password: String) = viewModelScope.launch {
        registerEventChannel.send(RegisterEvent.RegistrationSuccessful)
    }

    private fun sendInvalidInputMessage(text: String, invalidFor: InvalidInputFor) =
        viewModelScope.launch {
            registerEventChannel.send(
                RegisterEvent.ShowInvalidInputMessage(
                    text,
                    invalidFor
                )
            )
        }


    sealed class RegisterEvent {
        data class ShowInvalidInputMessage(val message: String, val invalidFor: InvalidInputFor) :
            RegisterEvent()

        object RegistrationSuccessful : RegisterEvent()
        data class RegistrationFailed(val message: String) : RegisterEvent()
    }
}

enum class InvalidInputFor { INVALID_EMAIL, INVALID_PASSWORD, INVALID_CONFIRM_PASSWORD, CONFIRM_PASSWORD_MISMATCH }