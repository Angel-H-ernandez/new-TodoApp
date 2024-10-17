package com.AngelHernandez.mytodoapp.data.network


import com.AngelHernandez.mytodoapp.data.model.TaskModel
import com.AngelHernandez.mytodoapp.data.model.UserModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClient {
    @GET("users")
    suspend fun signIn(
        @Query("email") email: String,
        @Query("password_hash") password: String,
        @Query("select") select: String = "*"
    ): List<UserModel>

    @POST("users")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest

    ): List<UserModel>

    @GET("task")
    suspend fun getTaskWorkSpace(
        @Query("group_task_id") id: String,
        @Query("completed") completed: String
    ): List<TaskModel>

    @POST("task")
    suspend fun addTask(
        @Body taskRequest: TaskRequest
    ): List<TaskModel>

    @PATCH("task")
    suspend fun completedTask(
        @Query("id") id: String,
        @Body task: TaskRequest
    ): List<TaskModel>

    @GET("users")
    suspend fun getIdGroupTaskDefault(
        @Query("id") id: String
    ): List<UserModel>


}

data class SignUpRequest(
    val username: String,
    val email: String,
    val password_hash: String
)

data class TaskRequest(
    val title : String,
    val group_task_id: Int,
    val assigned_user_id: Int,
    val created_by_user_id: Int,
    val completed: Boolean
)