package com.marteczek.todoappkotlin.service

import android.util.Log
import androidx.lifecycle.LiveData
import com.marteczek.todoappkotlin.database.dao.TodoDao
import com.marteczek.todoappkotlin.database.entity.Todo
import java.sql.SQLException

class TodoService(
    private val todoDao: TodoDao) {
    companion object{ const val TAG = "TodoService"}

    fun insertTodo(todo: Todo) {
        try {
            val id = todoDao.insert(todo)
            Log.d(TAG, "id = $id")
        } catch (e: SQLException) {
            Log.e(TAG, "SQLException")
        }
    }

    fun getTodos(): LiveData<List<Todo>> = todoDao.findAllAsync()
}