package com.AngelHernandez.mytodoapp.data.model


import java.sql.Date

data class TaskModel(
    val id: Int,
    val title: String,
    val description: String?,
    val created_at: String,
    val updated_at: String,
    val group_task_id: Int,
    val assigned_user_id: Int,
    val created_by_user_id: Int,
    val priority: Boolean?,
    val completed: Boolean,
    val progress_percentage: Int,
    val duration_time: Int,
    val task_agenda_id: Int


    /*
    *
     "id": 1,
     "title": "Comprar tortillas",
     "description": null,
     "completed": false,
     "created_at": "2024-08-28T17:51:09.159134+00:00",
     "updated_at": "2024-08-28T17:51:09.159134+00:00",
     "group_task_id": 3,
     "assigned_user_id": 1,
     "created_by_user_id": 1,
     "priority": null,
     "progress_percentage": 0,
     "duration_time": 30,
     "task_agenda_id": 1*/
)