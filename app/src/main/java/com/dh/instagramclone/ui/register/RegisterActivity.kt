package com.dh.instagramclone.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dh.instagramclone.MainActivity
import com.dh.instagramclone.R
import com.dh.instagramclone.databinding.ActivityRegisterBinding
import com.dh.instagramclone.util.exhaustive
import com.dh.instagramclone.util.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_InstagramClone)
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        binding.apply {
            btnRegister.setOnClickListener {
                hideKeyboard()
                viewModel.registerClicked(
                    edtEmail.text.toString(),
                    edtPassword.text.toString(),
                    edtConfirmPassword.text.toString()
                )
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.registerEvent.collect { event ->
                when (event) {
                    is RegisterViewModel.RegisterEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }
                    is RegisterViewModel.RegisterEvent.RegistrationSuccessful -> {
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    is RegisterViewModel.RegisterEvent.RegistrationFailed -> {

                    }
                }.exhaustive
            }
        }
    }
}