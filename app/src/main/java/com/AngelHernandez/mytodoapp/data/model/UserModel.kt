package com.AngelHernandez.mytodoapp.data.model;

import com.google.gson.annotations.SerializedName

data class UserModel(
        val id: Int,
        val username: String,
        val email: String,
        val password_hash: String,
        val created_at: String,
        val isPro: Boolean,
        val default_grouptask_id: Int
)