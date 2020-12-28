package com.dh.instagramclone.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dh.instagramclone.databinding.ActivityLoginBinding
import com.dh.instagramclone.ui.register.RegisterActivity
import com.dh.instagramclone.util.exhaustive
import com.dh.instagramclone.util.hideKeyboard
import com.dh.instagramclone.util.watchError
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
            edtUserName.watchError(true)
            edtPassword.watchError()
            btnLogin.setOnClickListener {
                viewModel.loginClicked(edtUserName.text.toString(), edtPassword.text.toString())
            }
            tvCreateAccount.setOnClickListener {
                val registerIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(registerIntent)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loginEvent.collect { event ->
                when (event) {
                    is LoginViewModel.LogInEvent.ShowInvalidMessage -> {
                        Toast.makeText(this@LoginActivity, event.message, Toast.LENGTH_LONG).show()
                        when (event.invalid) {
                            LoginViewModel.InvalidField.INVALID_USER_NAME -> {
                                binding.tilUserName.error = event.message
                                binding.edtUserName.requestFocus()
                            }
                            LoginViewModel.InvalidField.INVALID_PASSWORD -> {
                                binding.tilPassword.error = event.message
                                binding.edtPassword.requestFocus()
                            }
                        }
                    }
                }.exhaustive
            }
        }
    }
}