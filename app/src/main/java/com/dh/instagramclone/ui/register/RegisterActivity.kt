package com.dh.instagramclone.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dh.instagramclone.R
import com.dh.instagramclone.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_InstagramClone)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        binding.apply {
            btnRegister.setOnClickListener {

            }
        }
    }
}