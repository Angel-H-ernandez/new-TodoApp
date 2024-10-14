package com.AngelHernandez.mytodoapp.data.model


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserProvider private constructor() {
    private val _currentUser = MutableStateFlow<UserModel?>(null)
    val currentUser: StateFlow<UserModel?> = _currentUser

    fun setUser(user: UserModel) {
        _currentUser.value = user
    }

    fun clearUser() {
        _currentUser.value = null
    }

    companion object {
        @Volatile
        private var instance: UserProvider? = null

        fun getInstance(): UserProvider {
            return instance ?: synchronized(this) {
                instance ?: UserProvider().also { instance = it }
            }
        }
    }
}
