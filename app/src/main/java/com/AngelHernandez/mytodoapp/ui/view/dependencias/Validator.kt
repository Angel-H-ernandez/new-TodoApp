package com.AngelHernandez.mytodoapp.ui.view.dependencias

import java.util.regex.Pattern
import android.util.Patterns
import javax.inject.Inject

class Validator @Inject constructor() {



    fun isEmptyOrBlank(text: String): Boolean {
        return text.isEmpty() || text.isBlank()
    }

    fun isEmail(email: String): Boolean {
        return !isEmptyOrBlank(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}