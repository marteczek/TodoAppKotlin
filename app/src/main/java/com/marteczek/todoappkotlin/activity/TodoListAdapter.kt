package com.marteczek.todoappkotlin.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marteczek.todoappkotlin.R
import com.marteczek.todoappkotlin.database.entity.Todo
import com.marteczek.todoappkotlin.database.entity.type.TaskCategory

class TodoListAdapter(
    private val context: Context
): RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val dateFormat = android.text.format.DateFormat.getDateFormat(context)

    var todos: List<Todo>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = inflater.inflate(R.layout.recyclerview_todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todos?.size ?: 0
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo: Todo = todos?.get(position) ?: Todo(todoName = "")
        holder.nameTextView.text = todo.todoName
        val date = todo.completionDate
        holder.executionDateTextView.text = date?.let {dateFormat.format(it)}
        val category = when (todo.category) {
            TaskCategory.WORK -> context.getString(R.string.category_work)
            TaskCategory.SHOPPING -> context.getString(R.string.category_shopping)
            TaskCategory.OTHER -> context.getString(R.string.category_other)
            else -> ""
        }
        holder.categoryTextView.text = category
    }

    fun getTodo(position: Int): Todo? {
        return todos?.get(position)
    }

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.todo_name)
        val executionDateTextView: TextView = itemView.findViewById(R.id.completion_date)
        val categoryTextView: TextView = itemView.findViewById(R.id.category)
    }
}