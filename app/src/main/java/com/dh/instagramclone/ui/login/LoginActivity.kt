package com.dh.instagramclone.ui.login

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dh.instagramclone.R
import com.dh.instagramclone.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.loginClicked(edtUserName.text.toString(), edtPassword.text.toString())
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loginEvent.collect { event ->
                when(event){
                    is LoginViewModel.LogInEvent.ShowInvalidMessage -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}