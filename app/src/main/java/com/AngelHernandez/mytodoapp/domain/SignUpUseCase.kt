package com.AngelHernandez.mytodoapp.domain



import com.AngelHernandez.mytodoapp.data.UserRepository
import com.AngelHernandez.mytodoapp.data.model.UserModel
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(username: String, email: String, password: String): Result<UserModel>{
        return  repository.signUp(username, email, password);
    }
}