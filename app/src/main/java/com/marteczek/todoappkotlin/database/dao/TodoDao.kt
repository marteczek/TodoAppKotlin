package com.marteczek.todoappkotlin.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marteczek.todoappkotlin.database.entity.Todo

@Dao
interface TodoDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Todo):Long

    @Query("DELETE FROM todos WHERE id = :id")
    fun deleteById(id: Long);

    @Query("SELECT * FROM todos")
    fun findAll():List<Todo>

    @Query("SELECT * FROM todos")
    fun findAllAsync(): LiveData<List<Todo>>
}
