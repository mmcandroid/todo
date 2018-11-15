package com.example.malek.todo.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.malek.todo.R
import com.example.malek.todo.data.Todo

class TodoAdapter(var onTodoClick: OnTodoClickListener?) : RecyclerView.Adapter<TodoViewHolder>() {
    val todos = ArrayList<Todo>()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
        TodoViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.todo_item, p0, false))

    override fun getItemCount() = todos.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todo = todos[position])
        holder.itemView.setOnClickListener {
            onTodoClick?.onTodoClick(todo = todos[position])
        }
    }
}

class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(todo: Todo) {
        val title = itemView.findViewById<TextView>(R.id.title)
        title.text = todo.title

    }
}

interface OnTodoClickListener {
    fun onTodoClick(todo: Todo)
}