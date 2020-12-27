package com.dh.instagramclone.ui.login

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(@Assisted private val state: SavedStateHandle) :
    ViewModel() {

    private val loginChannel = Channel<LogInEvent>()
    val loginEvent = loginChannel.receiveAsFlow()

    fun loginClicked(userName: String, password: String) {
        if (userName.isBlank()) {
            showInvalidInputMessage("Please enter valid Username")
            return
        }
        if (password.isBlank()) {
            showInvalidInputMessage("Password can not be empty")
            return
        }
    }

    private fun showInvalidInputMessage(message: String) = viewModelScope.launch {
        loginChannel.send(LogInEvent.ShowInvalidMessage(message))
    }

    sealed class LogInEvent {
        data class ShowInvalidMessage(val message: String) : LogInEvent()
    }
}