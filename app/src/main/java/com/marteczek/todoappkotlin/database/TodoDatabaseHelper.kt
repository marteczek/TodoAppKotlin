package com.marteczek.todoappkotlin.database

interface TodoDatabaseHelper {
    fun execute(body: Runnable)
}