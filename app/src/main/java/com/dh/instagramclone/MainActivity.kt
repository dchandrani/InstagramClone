package com.dh.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.Theme_InstagramClone);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}