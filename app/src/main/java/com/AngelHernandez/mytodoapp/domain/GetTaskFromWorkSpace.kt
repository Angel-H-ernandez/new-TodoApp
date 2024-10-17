package com.AngelHernandez.mytodoapp.domain



import android.util.Log
import com.AngelHernandez.mytodoapp.data.TaskRepository
import com.AngelHernandez.mytodoapp.data.model.TaskModel
import javax.inject.Inject

class GetTaskFromWorkSpace @Inject constructor(private val repository: TaskRepository) {

    suspend operator fun invoke(id: String, status: Boolean) : Result<List<TaskModel>>{
        val task = repository.taskWorkSpace("eq."+ id, "eq."+status)
        Log.d("INFO use case", task.toString())
        return task
    }

}