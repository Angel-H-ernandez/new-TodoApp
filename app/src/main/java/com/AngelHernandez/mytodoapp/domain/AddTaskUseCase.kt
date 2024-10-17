package com.AngelHernandez.mytodoapp.domain



import android.util.Log
import com.AngelHernandez.mytodoapp.data.TaskRepository
import com.AngelHernandez.mytodoapp.data.model.TaskModel
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val repo: TaskRepository) {
    suspend operator fun invoke(nombre: String, idGroupTask: Int, idUserMain: Int) : Result<List<TaskModel>> {
        val task = repo.addTask(nombre, idGroupTask, idUserMain)
        Log.d("INFO use case", task.toString())
        return task
    }

}