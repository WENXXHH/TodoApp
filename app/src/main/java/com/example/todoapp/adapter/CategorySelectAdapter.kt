package com.example.todoapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.model.Category

/**
 * **`CategorySelectAdapter.kt`** - 分类选择适配器
 *    - 用于分类选择对话框
 *    - 支持单选功能
 *    - 显示选中状态
 */
class CategorySelectAdapter(
    private var categories: List<Category> = emptyList(),
    private var selectedCategoryId: Long = 0,
    private var onCategorySelected: (Category) -> Unit = {}
) : RecyclerView.Adapter<CategorySelectAdapter.CategorySelectViewHolder>() {

    /**
     * 分类选择ViewHolder
     */
    class CategorySelectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewColorIndicator: View = itemView.findViewById(R.id.view_color_indicator)
        val tvCategoryName: TextView = itemView.findViewById(R.id.tv_category_name)
        val ivSelected: ImageView = itemView.findViewById(R.id.iv_selected)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorySelectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_select, parent, false)
        return CategorySelectViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategorySelectViewHolder, position: Int) {
        val category = categories[position]
        val isSelected = category.id == selectedCategoryId

        holder.tvCategoryName.text = category.name

        try {
            holder.viewColorIndicator.setBackgroundColor(Color.parseColor(category.color))
        } catch (e: Exception) {
            holder.viewColorIndicator.setBackgroundColor(Color.parseColor("#6200EE"))
        }

        holder.ivSelected.visibility = if (isSelected) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            onCategorySelected(category)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    /**
     * 更新选中状态
     * @param categoryId 选中的分类ID
     */
    fun setSelectedCategory(categoryId: Long) {
        val oldSelectedId = selectedCategoryId
        selectedCategoryId = categoryId

        // 找出变化的索引并更新
        categories.forEachIndexed { index, category ->
            if (category.id == oldSelectedId || category.id == categoryId) {
                notifyItemChanged(index)
            }
        }
    }

    /**
     * 更新分类数据
     * @param newCategories 新的分类列表
     * @param newSelectedCategoryId 选中的分类ID
     */
    fun updateData(newCategories: List<Category>, newSelectedCategoryId: Long = 0) {
        categories = newCategories
        selectedCategoryId = newSelectedCategoryId
        notifyDataSetChanged()
    }

    /**
     * 获取选中的分类ID
     * @return 选中的分类ID，如果没有选中则返回0
     */
    fun getSelectedCategoryId(): Long {
        return selectedCategoryId
    }

    /**
     * 设置分类选择监听器
     * @param listener 选择监听器
     */
    fun setOnCategorySelectedListener(listener: (Category) -> Unit) {
        onCategorySelected = listener
    }
}
