package com.marteczek.todoappkotlin.service

import com.marteczek.todoappkotlin.database.entity.Todo

data class SaveTodoStatus(
    val status: Boolean,
    val todo: Todo
)