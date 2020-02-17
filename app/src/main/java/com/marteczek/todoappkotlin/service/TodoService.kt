package com.marteczek.todoappkotlin.service

import android.util.Log
import com.marteczek.todoappkotlin.database.dao.TodoDao
import com.marteczek.todoappkotlin.database.entity.Todo
import java.sql.SQLException

class TodoService(
    private val todoDao: TodoDao) {
    companion object{ const val TAG = "TodoService"}

    fun insertTodo(todo: Todo) {
        try {
            todoDao.insert(todo)
        } catch (e: SQLException) {
            Log.e(TAG, "SQLException")
        }
    }
}