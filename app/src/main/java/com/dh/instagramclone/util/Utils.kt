package com.dh.instagramclone.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

val <T> T.exhaustive: T
    get() = this

fun Activity.hideKeyboard(
) {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showKeyboard(
) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus ?: View(this)
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun TextInputEditText.addListener() {
    addTextChangedListener {
        val text = text.toString()
        if (text.isNotBlank()) {
            val til = parent.parent as TextInputLayout
            til.error = null
        }
    }
}