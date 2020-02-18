package com.marteczek.todoappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.marteczek.todoappkotlin.database.TodoDatabase
import com.marteczek.todoappkotlin.database.entity.Todo

import com.marteczek.todoappkotlin.service.TodoService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TodoEditorViewModel(application: Application): AndroidViewModel(application) {
    //TODO
    private val todoDao = TodoDatabase.getDatabase(application).todoDao()
    private val todoService = TodoService(todoDao)

    fun insertTodo(todo: Todo) {
//        viewModelScope.launch {
//            todoService.insertTodo(todo)
//        }
        GlobalScope.launch {
            todoService.insertTodo(todo)
        }
    }
}