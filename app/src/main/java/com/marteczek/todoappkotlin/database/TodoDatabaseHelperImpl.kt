package com.marteczek.todoappkotlin.database

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TodoDatabaseHelperImpl(
    val db: TodoDatabase
): TodoDatabaseHelper {

    override fun execute(body: Runnable) {
        GlobalScope.launch {body.run()}
    }
}
