package com.marteczek.todoappkotlin.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marteczek.todoappkotlin.database.TodoDatabaseHelper
import com.marteczek.todoappkotlin.database.dao.TodoDao
import com.marteczek.todoappkotlin.database.entity.Todo

class TodoService(
    private val todoDao: TodoDao,
    private val dbHelper: TodoDatabaseHelper) {
    companion object{ const val TAG = "TodoService"}

    fun insertTodo(todo: Todo): LiveData<SaveTodoStatus> {
        val status = MutableLiveData<SaveTodoStatus>()
        dbHelper.execute(Runnable {
            try {
                val id = todoDao.insert(todo)
                val error = -1L
                if (id != error) {
                    status.postValue(SaveTodoStatus(true, todo))
                } else {
                    status.postValue(SaveTodoStatus(false, todo))
                }
            } catch (e: android.database.SQLException) {
                Log.e(TAG, "SQLException during row inserting")
                status.postValue(SaveTodoStatus(false, todo))
            }
        })
        return status
    }

    fun getTodos(): LiveData<List<Todo>> = todoDao.findAllAsync()
}