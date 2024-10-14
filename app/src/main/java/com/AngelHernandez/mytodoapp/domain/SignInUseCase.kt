package com.AngelHernandez.mytodoapp.domain

import android.util.Log
import com.AngelHernandez.mytodoapp.data.UserRepository
import com.AngelHernandez.mytodoapp.data.model.UserModel
import javax.inject.Inject


class SignInUseCase @Inject constructor(private val repository: UserRepository) {
    //esto es una funcion asincrona, el invoke se llama automaticamente como un main
    suspend operator fun invoke(email: String, password: String): Result<UserModel> {
        Log.i("INFO", "estoy en el use cASE")
        return repository.signIn("eq."+ email, "eq."+password)
    }
}


