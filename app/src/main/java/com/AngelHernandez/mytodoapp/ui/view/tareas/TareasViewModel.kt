package com.AngelHernandez.mytodoapp.ui.view.tareas



import android.app.AlertDialog
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AngelHernandez.mytodoapp.data.model.TaskModel
import com.AngelHernandez.mytodoapp.data.model.UserModel
import com.AngelHernandez.mytodoapp.domain.CompletedTaskUseCase
import com.AngelHernandez.mytodoapp.domain.GetTaskFromWorkSpace
import com.AngelHernandez.mytodoapp.domain.AddTaskUseCase
import com.AngelHernandez.mytodoapp.ui.view.dependencias.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareasViewModel @Inject constructor(private val getTaskFromWorkSpace: GetTaskFromWorkSpace,
                                          private val addTaskUseCase: AddTaskUseCase,
                                          private val completedTaskUseCase: CompletedTaskUseCase,
                                          private val preferencesManager: PreferencesManager) : ViewModel() {

    //estateflow es mas modernos que live data
    private val _taskResult = MutableStateFlow<List<TaskModel>>(emptyList())
    val taskResult: StateFlow<List<TaskModel>> = _taskResult

    // LiveData para manejar el estado de la carga (opcional)
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _dialogEvent = MutableStateFlow<DialogEvent?>(null)
    val dialogEvent: StateFlow<DialogEvent?> = _dialogEvent

    init { //este metodo se llama acutomasticamente al llamar al viewmodel, como un oncreate de una vista

        viewModelScope.launch{ //corrutina
            //marcar que esta cargando
            _isLoading.value = true;
            try {
                //POR DEFECTO OBTENER LAS TAREAS DEL GRUPO DEFAULT
                val result = getTaskFromWorkSpace(preferencesManager.getIdGroupTaskDefault()!!, false) // mandar al caso de uso los datos
                _taskResult.value = result.getOrNull()!!;
                Log.i("INFO vieemodel", _taskResult.value.toString())
            }catch(e: Exception){
                Log.e("INFO", "Exception occurred during Get task: ${e.message}")
            }finally {
                _isLoading.value = false;
            }

        }
    }

    fun addTask(nombre: String){
        if (nombre.isEmpty()) {
            _dialogEvent.value = DialogEvent("Advertencia", "El nombre de la tarea no puede estar vacío")
            return
        }
        viewModelScope.launch{

            _isLoading.value = true

            try {
                val idGroupTaskDefault =  preferencesManager.getIdGroupTaskDefault()!!
                val idUser = preferencesManager.getId()!!

                val result = addTaskUseCase(nombre, idGroupTaskDefault.toInt(), idUser.toInt())
                // _taskResult.value = result.getOrNull()!!
                Log.i("INFO vieemodel", _taskResult.value.toString())
                Log.i("INFO vieemodel tarea nueva", result.toString())
                _taskResult.value += result.getOrNull()!!

            }catch(e: Exception){
                Log.e("INFO", "Exception occurred during Get task: ${e.message}")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun completedTask(task : TaskModel){
        viewModelScope.launch{
            try{

                val result = completedTaskUseCase(task)
                Log.i("INFO COMPLETED TASK", result.toString())
                _taskResult.value -= result.getOrNull()!!
                Log.i("INFO LIST TASK", _taskResult.value.toString())

                var idTaskCompleted = 0

                // Verifica si el resultado es exitoso
                result.onSuccess { tasks ->

                    if (tasks.isNotEmpty()) {
                        val firstTask = tasks.first() // Obtiene el primer elemento
                        idTaskCompleted = firstTask.id
                        _taskResult.value -= firstTask
                    } else {
                        Log.i("INFO", "La lista de tareas está vacía.")
                    }
                }.onFailure { exception ->
                    Log.e("ERROR", "Failed to complete task: ${exception.message}")
                }


                val listTaskModel = _taskResult.value?.filter { task ->
                    task.id != idTaskCompleted // Incluye solo las tareas cuyo id no sea 12
                } ?: emptyList() // Si _taskResult.value es nulo, crea una lista vacía

                _taskResult.value = listTaskModel


            }catch(e: Exception){
                Log.e("INFO", "Exception occurred during Get task: ${e.message}")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun dialogShown() {
        _dialogEvent.value = null
    }




}
data class DialogEvent(val title: String, val message: String)