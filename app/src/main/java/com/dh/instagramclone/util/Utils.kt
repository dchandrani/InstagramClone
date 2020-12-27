package com.dh.instagramclone.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

val <T> T.exhaustive: T
    get() = this

fun Activity.hideKeyboard(
) {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as
            InputMethodManager
    val view = this.currentFocus ?: View(this)

    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun TextInputEditText.watchError(isRequired: Boolean = false) {
    addTextChangedListener {
        val value = text.toString()
        if (value.isNotBlank()) {
            val view = parent.parent as TextInputLayout
            view.error = ""
        } else if (isRequired) {
            val view = parent.parent as TextInputLayout
            view.error = "$hint is required"
        }
    }
}