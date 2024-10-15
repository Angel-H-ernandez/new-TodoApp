package com.AngelHernandez.mytodoapp.ui.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AngelHernandez.mytodoapp.data.model.UserModel
import com.AngelHernandez.mytodoapp.domain.SignInUseCase
import com.AngelHernandez.mytodoapp.ui.view.dependencias.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
//el inject constructor indica las depenendecias que necesita esta clas
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase,
                                          private val validator: Validator):ViewModel() {

    // LiveData para observar el estado del inicio de sesión
    private val _signInResult = MutableLiveData<Result<UserModel>>()
    val signInResult: LiveData<Result<UserModel>> get() = _signInResult

    // LiveData para manejar el estado de la carga (opcional)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading




    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true // Indica que se está procesando la solicitud

            if (!validator.isEmail(email)) {
                _isLoading.value = false
                _signInResult.value = Result.failure(Exception("Invalid email format")) // Comunica el error a la vista
                return@launch // Termina la función si la validación falla
            }
            try {
                val result = signInUseCase(email, password) // Llama al caso de uso
                _signInResult.value = result // Actualiza el resultado del inicio de sesión

                // Puedes manejar el resultado aquí, por ejemplo, redirigir al usuario
                if (result.isSuccess) {
                    Log.i("INFO", "Login successful: ${result.getOrNull()}")
                } else {
                    Log.e("INFO", "Login failed: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                // Manejo de excepciones generales
                Log.e("INFO", "Exception occurred during sign in: ${e.message}")
                _signInResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false // Indica que la solicitud ha terminado
            }
        }
    }



}







