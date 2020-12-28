package com.dh.instagramclone.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.dh.instagramclone.MainActivity
import com.dh.instagramclone.R
import com.dh.instagramclone.databinding.ActivityRegisterBinding
import com.dh.instagramclone.util.addListener
import com.dh.instagramclone.util.exhaustive
import com.dh.instagramclone.util.showKeyboard
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            edtEmail.addListener()
            edtPassword.addListener()
            edtConfirmPassword.addListener()

            btnRegister.setOnClickListener {
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
                        val textInputLayout: TextInputLayout = when (event.invalidFor) {
                            InvalidInputFor.INVALID_EMAIL -> {
                                binding.tilEmail
                            }
                            InvalidInputFor.INVALID_PASSWORD -> {
                                binding.tilPassword
                            }
                            InvalidInputFor.INVALID_CONFIRM_PASSWORD -> {
                                binding.tilConfirmPassword
                            }
                            InvalidInputFor.CONFIRM_PASSWORD_MISMATCH -> {
                                binding.tilConfirmPassword
                            }
                        }

                        textInputLayout.requestFocus()
                        textInputLayout.error = event.message
                        showKeyboard()
                    }
                    is RegisterViewModel.RegisterEvent.RegistrationSuccessful -> {
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is RegisterViewModel.RegisterEvent.RegistrationFailed -> {

                    }
                }.exhaustive
            }
        }
    }
}