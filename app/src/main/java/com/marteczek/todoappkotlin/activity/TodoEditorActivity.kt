package com.marteczek.todoappkotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.marteczek.todoappkotlin.R
import com.marteczek.todoappkotlin.database.entity.Todo
import com.marteczek.todoappkotlin.viewmodel.TodoEditorViewModel
import kotlinx.android.synthetic.main.activity_todo_editor.*

class TodoEditorActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(TodoEditorViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_editor)
        bindButtons()
    }

    private fun bindButtons() {
        saveButton.setOnClickListener(this::saveTodo)
        cancelButton.setOnClickListener(this::cancel)
    }

    fun saveTodo(v: View) {
        val todoName = todoNameEditText.text.toString()
        if (!TextUtils.isEmpty(todoName)){
            val todo = Todo( todoName = todoName)
            viewModel.insertTodo(todo)
            finish()
        }
    }

    fun cancel(v: View) {
        finish()
    }
}
