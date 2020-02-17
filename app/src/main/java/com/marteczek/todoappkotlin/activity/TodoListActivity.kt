package com.marteczek.todoappkotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marteczek.todoappkotlin.R
import kotlinx.android.synthetic.main.activity_todo_list.*

class TodoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)
        emptyListInfoTextView.text = "asdfsadfa"
    }
}
