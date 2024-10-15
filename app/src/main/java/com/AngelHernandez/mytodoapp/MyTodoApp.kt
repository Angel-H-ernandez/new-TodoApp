package com.AngelHernandez.mytodoapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp //perapara daggerhilt
class MyTodoApp: Application() {

    fun saveID(context: Context, id: String) {
        val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id", id)
        editor.apply() // o editor.commit() para una escritura síncrona
    }

    fun getID(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("id", null) // null es el valor por defecto si no existe
    }



}