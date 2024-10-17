package com.AngelHernandez.mytodoapp.ui.view.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AngelHernandez.mytodoapp.data.model.TaskModel
import com.AngelHernandez.mytodoapp.data.model.UserModel
import com.AngelHernandez.mytodoapp.domain.GetIdGroupTaskDefaultUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getIdGropTaskDefaultUseCase: GetIdGroupTaskDefaultUseCase
) : ViewModel(){

    private val _idGroupTaskDefaultResult = MutableLiveData<Result<UserModel>>()
    val idGroupTaskDefaultResult: MutableLiveData<Result<UserModel>> = _idGroupTaskDefaultResult

    fun getIdGroupTaskDefault(id: String){
        viewModelScope.launch{

            try {

                val result = getIdGropTaskDefaultUseCase(id)
                Log.i("INFO vieemodel tarea nueva", result.toString())
                _idGroupTaskDefaultResult.value = result

            }catch(e: Exception){
                Log.e("INFO", "Exception occurred during Get task: ${e.message}")
            }finally {
                Log.i("INFO", "termiono la consulta de viewmodel del home")
            }
        }
    }




}