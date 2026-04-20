package com.example.todoapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.model.Category

/**
 * **`CategoryAdapter.kt`** - 分类列表适配器
 *    - 管理分类数据展示
 *    - 处理分类选择
 *    - 支持动态更新
 *    - 优化UI交互
 */
class CategoryAdapter(
    private var categories: List<Category> = emptyList(),
    private var todoCounts: Map<Long, Int> = emptyMap(),
    private val onCategoryClick: (Category) -> Unit = {}
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    /**
     * 分类ViewHolder
     */
    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewColorIndicator: View = itemView.findViewById(R.id.view_color_indicator)
        val tvCategoryName: TextView = itemView.findViewById(R.id.tv_category_name)
        val tvTodoCount: TextView = itemView.findViewById(R.id.tv_todo_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        holder.tvCategoryName.text = category.name

        val count = todoCounts[category.id] ?: 0
        holder.tvTodoCount.text = "${count}个待办"

        try {
            holder.viewColorIndicator.setBackgroundColor(Color.parseColor(category.color))
        } catch (e: Exception) {
            holder.viewColorIndicator.setBackgroundColor(Color.parseColor("#6200EE"))
        }

        holder.itemView.setOnClickListener {
            onCategoryClick(category)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    /**
     * 更新分类数据
     * @param newCategories 新的分类列表
     * @param newTodoCounts 新的待办数量映射
     */
    fun updateData(newCategories: List<Category>, newTodoCounts: Map<Long, Int> = emptyMap()) {
        categories = newCategories
        todoCounts = newTodoCounts
        notifyDataSetChanged()
    }
}
