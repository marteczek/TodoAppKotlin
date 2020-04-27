package com.marteczek.todoappkotlin.activity

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marteczek.todoappkotlin.R
import com.marteczek.todoappkotlin.database.entity.Todo
import com.marteczek.todoappkotlin.database.entity.type.TaskCategory
import com.marteczek.todoappkotlin.service.SaveTodoStatus
import com.marteczek.todoappkotlin.viewmodel.TodoEditorViewModel
import kotlinx.android.synthetic.main.activity_todo_editor.*
import java.lang.IllegalStateException
import java.util.*


class TodoEditorActivity : AppCompatActivity() {
    companion object {
        const val STATE_COMPLETION_DATE = "state_completed_date"
    }

    private var completionDate: Date? = null

    private val viewModel by lazy {
        ViewModelProvider(this).get(TodoEditorViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_editor)
        bindUIViews()
        configureCategorySpinner()
        bindViewModelData()
    }

    private fun bindUIViews() {
        saveButton.setOnClickListener(this::saveTodo)
        cancelButton.setOnClickListener(this::cancel)
        completionDateTextView.setOnClickListener(this::enterCompletionDate)
    }

    private fun configureCategorySpinner() {
        val categoryList = mapOf(
            TaskCategory.OTHER to getString(R.string.category_other),
            TaskCategory.WORK to getString(R.string.category_work),
            TaskCategory.SHOPPING to getString(R.string.category_shopping)
        ).map { (k, v) -> CategoryItem(k, v)}
        val categoryAdapter: ArrayAdapter<CategoryItem> = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, categoryList)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter
    }

    private fun bindViewModelData() {
        viewModel.lastInsertTodoStatus?.let{it. observe(this, Observer {result -> onTodoSavingResult(result)})}
    }

    private fun saveTodo(v: View) {
        val todoName = todoNameTextView.text.toString()
        if (!TextUtils.isEmpty(todoName)) {
            val categoryItem = categorySpinner.selectedItem as CategoryItem?
            val todo = Todo(
                todoName = todoName,
                completionDate = completionDate,
                category = categoryItem?.key)
            viewModel.insertTodo(todo).observe(this, Observer {result -> onTodoSavingResult(result)})
        }
    }

    private fun onTodoSavingResult(saveTodoStatus: SaveTodoStatus) {
        if (saveTodoStatus.status) {
            Toast.makeText(this, R.string.todo_has_been_added, Toast.LENGTH_SHORT).show()
            finish()
        } else {
            showDialogRetryTodoSaving(saveTodoStatus.todo)
        }
    }

    private fun showDialogRetryTodoSaving(todo: Todo) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.saving)
            .setMessage(R.string.question_retry_save)
            .setPositiveButton(R.string.yes) {_, _ ->
                this.viewModel.insertTodo(todo).observe(this, Observer {
                        result -> this.onTodoSavingResult(result)
                })
            }
            .setNegativeButton(R.string.no) {_, _ -> this.finish()}
        builder.show()
    }

    private fun cancel(v: View) {
        finish()
    }

    private fun enterCompletionDate(v: View) {
        DatePickerDialogFragment().show(supportFragmentManager, "")
    }

    private fun updateCompletionDate() {
        val dateFormat = DateFormat.getDateFormat(applicationContext)
        if (completionDate!= null) {
            completionDateTextView.setText(dateFormat.format(completionDate!!))
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val date = savedInstanceState.getLong(STATE_COMPLETION_DATE, -1)
        if (date != -1L) {
            completionDate = Date(date)
            updateCompletionDate()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        if (completionDate != null) {
            outState.putLong(STATE_COMPLETION_DATE, completionDate!!.time)
        }
    }

    private data class CategoryItem(
        val key: String,
        val text: String) {
        override fun toString() = text
    }

    class DatePickerDialogFragment : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val activity = it as TodoEditorActivity
                val calendar = Calendar.getInstance()
                activity.completionDate?.let {t -> calendar.time = t}
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                DatePickerDialog(it,
                    { _, y, m, d ->
                        activity.completionDate = GregorianCalendar(y, m, d).time
                        activity.updateCompletionDate()
                    }, year, month, day
                )
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }
}
