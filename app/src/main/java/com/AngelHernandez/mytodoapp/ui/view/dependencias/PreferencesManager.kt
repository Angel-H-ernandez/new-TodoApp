package com.AngelHernandez.mytodoapp.ui.view.dependencias

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun saveId(id: String) {
        val editor = sharedPreferences.edit()
        editor.putString("ID", id)
        editor.apply()
    }

    fun getId(): String? {
        return sharedPreferences.getString("ID", null)
    }

    fun saveIdGroupTaskDefault(id: String) {
        val editor = sharedPreferences.edit()
        editor.putString("IdGroupTaskDefault", id)
        editor.apply()
    }

    fun getIdGroupTaskDefault(): String? {
        return sharedPreferences.getString("IdGroupTaskDefault", null)
    }
}
