package com.AngelHernandez.mytodoapp.ui.view.tareas.adapter



import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AngelHernandez.mytodoapp.R
import com.AngelHernandez.mytodoapp.data.model.TaskModel

class TaskAdapter (private var taskList : List<TaskModel> = emptyList(), private val completedTaskFunction:(TaskModel)->Unit) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    //le dice al view holder que pintar
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        Log.d("RENDER2", "onBindViewHolder")
        holder.render(taskList[position], completedTaskFunction)
    }

    fun updateTasks(tasks: List<TaskModel>) {
        taskList = tasks
        notifyDataSetChanged()
    }

}
