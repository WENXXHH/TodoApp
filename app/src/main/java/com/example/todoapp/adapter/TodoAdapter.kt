package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.utils.DateTimeUtils

/**
 * **`TodoAdapter.kt`** - 待办事项列表适配器
 *    - 绑定数据到视图
 *    - 处理事项完成状态切换
 *    - 实现项点击和长按
 *    - 支持动态更新数据
 */
class TodoAdapter(
    private var todos: List<TodoItem>,
    private val onTodoClick: (TodoItem) -> Unit,
    private val onTodoLongClick: (TodoItem) -> Boolean,
    private val onCompleteToggle: (TodoItem) -> Unit,
    private val onEditClick: (TodoItem) -> Unit,
    private val onDeleteClick: (TodoItem) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    /**
     * 视图持有者
     */
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cbCompleted: CheckBox = itemView.findViewById(R.id.cb_completed)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btn_edit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete)
    }

    /**
     * 创建视图持有者
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    /**
     * 绑定数据到视图
     */
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]
        
        // 绑定数据
        holder.cbCompleted.isChecked = todo.isCompleted
        holder.tvTitle.text = todo.title
        holder.tvDescription.text = todo.description
        
        // 格式化时间显示
        if (todo.dueTime > 0) {
            holder.tvTime.text = DateTimeUtils.formatDateTime(todo.dueTime)
        } else {
            holder.tvTime.text = DateTimeUtils.formatDate(todo.createdAt)
        }
        
        // 处理完成状态切换
        holder.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked != todo.isCompleted) {
                onCompleteToggle(todo)
            }
        }
        
        // 处理编辑点击
        holder.btnEdit.setOnClickListener {
            onEditClick(todo)
        }
        
        // 处理删除点击
        holder.btnDelete.setOnClickListener {
            onDeleteClick(todo)
        }
        
        // 处理项点击
        holder.itemView.setOnClickListener {
            onTodoClick(todo)
        }
        
        // 处理项长按
        holder.itemView.setOnLongClickListener {
            onTodoLongClick(todo)
        }
    }

    /**
     * 获取数据项数量
     */
    override fun getItemCount(): Int {
        return todos.size
    }

    /**
     * 更新数据
     */
    fun updateTodos(newTodos: List<TodoItem>) {
        todos = newTodos
        notifyDataSetChanged()
    }

    /**
     * 获取当前数据
     */
    fun getTodos(): List<TodoItem> {
        return todos
    }
}

