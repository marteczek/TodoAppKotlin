package com.marteczek.todoappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.marteczek.todoappkotlin.database.TodoDatabase
import com.marteczek.todoappkotlin.database.TodoDatabaseHelperImpl
import com.marteczek.todoappkotlin.database.entity.Todo
import com.marteczek.todoappkotlin.service.TodoService

class TodoListViewModel (application: Application): AndroidViewModel(application) {
    //TODO
    private val todoDao = TodoDatabase.getDatabase(application).todoDao()
    private val todoService = TodoService(todoDao, TodoDatabaseHelperImpl(TodoDatabase.getDatabase(application)))
    private val todosLiveData: LiveData<List<Todo>> by lazy { todoService.getTodos() }

    fun getTodos() = todosLiveData
}