package com.marteczek.todoappkotlin.activity

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val callback: ItemTouchHelper.Callback = ItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    inner class ItemTouchHelperCallback(
        val adapter: TodoListAdapter
    ) : ItemTouchHelper.Callback() {

        override fun isItemViewSwipeEnabled() = true

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder)
                = makeMovementFlags(0, ItemTouchHelper.END)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.getTodo(viewHolder.adapterPosition)?.let{viewModel.deleteTodo(it)}
        }

        override fun onChildDraw(
            c: Canvas, recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
        ) {
            val itemView = viewHolder.itemView
            if (dX > 0) {
                // draw a red background with a trash can while sliding
                val p = Paint()
                p.setARGB(0xff, 0xff, 0, 0)
                c.drawRect(
                    itemView.left.toFloat(), itemView.top.toFloat(), dX,
                    itemView.bottom.toFloat(), p
                )
                val drawable = AppCompatResources.getDrawable(
                    this@TodoListActivity,
                    R.drawable.ic_delete_white_36dp
                )
                if (drawable != null) {
                    val oneDPI: Float = getResources().getDisplayMetrics().xdpi /
                            DisplayMetrics.DENSITY_DEFAULT
                    val margin = Math.round(16 * oneDPI)
                    val dim = Math.round(24 * oneDPI)
                    val mid = (itemView.top + itemView.bottom) / 2
                    drawable.setBounds(
                        itemView.left + margin, mid - dim / 2,
                        itemView.left + margin + dim, mid + dim / 2
                    )
                    c.clipRect(
                        itemView.left.toFloat(),
                        itemView.top.toFloat(),
                        dX,
                        itemView.bottom.toFloat()
                    )
                    drawable.draw(c)
                }
                var alpha = 1f
                if (dX < itemView.width) {
                    alpha = 1f - Math.abs(dX) / itemView.width.toFloat()
                }
                itemView.alpha = alpha
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
}
