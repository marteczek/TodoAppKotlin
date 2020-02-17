package com.marteczek.todoappkotlin.database.entity

import androidx.room.TypeConverter
import java.util.*

class Converter {

    @TypeConverter
    fun timestampToDate(value: Long?) = value?.let {Date(value)}

    @TypeConverter
    fun dateToTimestamp(value: Date?) = value?.time
}