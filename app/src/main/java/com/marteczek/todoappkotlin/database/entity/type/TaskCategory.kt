package com.marteczek.todoappkotlin.database.entity.type

import androidx.annotation.StringDef
import com.marteczek.todoappkotlin.database.entity.type.TaskCategory.Companion.OTHER
import com.marteczek.todoappkotlin.database.entity.type.TaskCategory.Companion.SHOPPING
import com.marteczek.todoappkotlin.database.entity.type.TaskCategory.Companion.WORK

@Retention(AnnotationRetention.SOURCE)
@StringDef(WORK, SHOPPING, OTHER)
annotation class TaskCategory{
    companion object {
        const val WORK = "category_work"
        const val SHOPPING = "category_shopping"
        const val OTHER = "category_other"
    }
}
