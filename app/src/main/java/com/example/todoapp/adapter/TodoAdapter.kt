package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 0
    }
}
