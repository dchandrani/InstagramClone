package com.dh.instagramclone.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText

val <T> T.exhaustive: T
    get() = this

fun Activity.hideKeyboard(
) {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as
            InputMethodManager
    val view = this.currentFocus ?: View(this)

    imm.hideSoftInputFromWindow(view.windowToken, 0)
}