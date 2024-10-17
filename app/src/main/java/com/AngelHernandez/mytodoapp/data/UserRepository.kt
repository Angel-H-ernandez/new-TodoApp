package com.AngelHernandez.mytodoapp.data


import android.util.Log
import com.AngelHernandez.mytodoapp.data.model.UserModel
import com.AngelHernandez.mytodoapp.data.network.ApiClient
import com.AngelHernandez.mytodoapp.data.network.SignUpRequest
import javax.inject.Inject




class UserRepository @Inject constructor(private val apiClient: ApiClient) {
    suspend fun signIn(email: String, password: String): Result<UserModel> {
        return try {
            Log.i("INFO", "Attempting sign in at REPOSITORY")
            val users = apiClient.signIn(email, password)
            if (users.isNotEmpty()) {
                Result.success(users.first()) // Obtener el primer usuario si hay usuarios
            } else {
                Result.failure(Exception("No users found"))
            }
        } catch (e: Exception) {
            Log.e("INFO", "Sign in failed: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun signUp(name: String, email: String, password: String): Result<UserModel> {
        return try {

            val signUpRequest = SignUpRequest(
                username = name,
                email = email,
                password_hash = password
            )

            val user = apiClient.signUp(signUpRequest)
            if (user.isNotEmpty()){
                Result.success(user.first())
            }else{
                Result.failure(Exception("Error whilw create user"))
            }
        }catch (e: Exception){
            Log.e("INFO", "Sign up failed: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getIdGroupTaskDefault(id: String): Result<UserModel> {
        return try {
            Log.i("INFO", "Attempting sign in at REPOSITORY")
            val user = apiClient.getIdGroupTaskDefault(id)
            if (user.isNotEmpty()) {
                Result.success(user.first()) // Obtener el primer usuario si hay usuarios
            } else {
                Result.failure(Exception("No users found"))
            }
        } catch (e: Exception) {
            Log.e("INFO", "Sign in failed: ${e.message}")
            Result.failure(e)
        }
    }
}





