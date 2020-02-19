package com.marteczek.todoappkotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.marteczek.todoappkotlin.R
import com.marteczek.todoappkotlin.viewmodel.TodoListViewModel
import kotlinx.android.synthetic.main.activity_todo_list.*

class TodoListActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(TodoListViewModel::class.java)
    }

    // onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)
        configureRecyclerView()
        bindButtons()
    }

    private fun bindButtons() {
        addTodoButton.setOnClickListener(this::newTodo)
    }

    private fun newTodo (v: View) {
        intent = Intent(this, TodoEditorActivity::class.java)
        startActivity(intent)
    }

    private fun configureRecyclerView() {
        val adapter = TodoListAdapter(this)
        recyclerView.adapter = adapter
        viewModel.getTodos().observe(this, Observer {todos -> adapter.todos = todos})
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
