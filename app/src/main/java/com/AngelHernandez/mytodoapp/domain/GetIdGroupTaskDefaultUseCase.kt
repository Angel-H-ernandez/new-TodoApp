package com.AngelHernandez.mytodoapp.domain

import android.util.Log
import com.AngelHernandez.mytodoapp.data.UserRepository
import com.AngelHernandez.mytodoapp.data.model.UserModel
import javax.inject.Inject

class GetIdGroupTaskDefaultUseCase @Inject constructor(
    private val repository: UserRepository) {
    suspend operator fun invoke(id: String): Result<UserModel> {
        Log.i("INFO", "estoy en el use cASE")
        return repository.getIdGroupTaskDefault("eq."+id)
    }
}