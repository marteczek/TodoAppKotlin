package com.marteczek.todoappkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marteczek.todoappkotlin.database.dao.TodoDao
import com.marteczek.todoappkotlin.database.entity.Converter
import com.marteczek.todoappkotlin.database.entity.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class TodoDatabase: RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun todoDao(): TodoDao
}
