package com.marteczek.todoappkotlin.service

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marteczek.todoappkotlin.database.TodoDatabase
import com.marteczek.todoappkotlin.database.dao.TodoDao
import com.marteczek.todoappkotlin.database.entity.Todo
import com.marteczek.todoappkotlin.database.entity.type.TaskCategory
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class TodoServiceTest {

    private lateinit var context: Context

    private lateinit var db: TodoDatabase

    private lateinit var todoDao: TodoDao

    @Before
    fun createDb() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, TodoDatabase::class.java).build()
        todoDao = db.todoDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertTodo() {
        //given
        val name = "name"
        val date = Date(0)
        val category = TaskCategory.OTHER
        val todo = Todo(todoName = name, completionDate = date, category = category)
        val todoService = TodoService(todoDao)
        //when
        todoService.insertTodo(todo)
        //then
        val todos = todoDao.findAll()
        assertEquals(1, todos.size)
        val newTodo = todos.get(0)
        assertEquals(name, todo.todoName)
        assertEquals(date, todo.completionDate)
        assertEquals(category, todo.category)
    }

    @Test
    fun insertTodo_nullColumns() {
        //given
        val name = "name"
        val todo = Todo(todoName = name)
        val todoService = TodoService(todoDao)
        //when
        todoService.insertTodo(todo)
        //then
        val todos = todoDao.findAll()
        assertEquals(1, todos.size)
        val newTodo = todos.get(0)
        assertEquals(name, todo.todoName)
        assertNull(todo.completionDate)
        assertNull(todo.category)
    }

}