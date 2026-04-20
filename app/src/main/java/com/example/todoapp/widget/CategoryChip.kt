package com.example.todoapp.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.example.todoapp.R
import com.example.todoapp.data.model.Category

/**
 * **`CategoryChip.kt`** - 自定义分类标签组件
 *    - 显示分类名称和颜色
 *    - 支持点击事件
 *    - 提供选中状态
 */
class CategoryChip @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var category: Category? = null
    private var isSelectedState = false

    init {
        // 初始化默认样式
        setPadding(12, 6, 12, 6)
        gravity = Gravity.CENTER
        textSize = 12f
        setBackgroundResource(R.drawable.bg_category_chip)
    }

    /**
     * 设置分类
     * @param category 分类
     */
    fun setCategory(category: Category) {
        this.category = category
        text = category.name
        setChipColor(category.color)
    }

    /**
     * 设置标签颜色
     * @param color 颜色值
     */
    fun setChipColor(color: String) {
        try {
            setTextColor(Color.parseColor(color))
        } catch (e: Exception) {
            setTextColor(Color.BLACK)
        }
    }

    /**
     * 设置选中状态
     * @param selected 是否选中
     */
    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        isSelectedState = selected
        updateSelectedState()
    }

    /**
     * 更新选中状态
     */
    private fun updateSelectedState() {
        if (isSelectedState) {
            setBackgroundResource(R.drawable.bg_category_chip_selected)
        } else {
            setBackgroundResource(R.drawable.bg_category_chip)
        }
    }

    /**
     * 获取当前分类
     * @return 分类
     */
    fun getCategory(): Category? {
        return category
    }

    /**
     * 检查是否为空
     * @return 是否为空
     */
    fun isEmpty(): Boolean {
        return category == null
    }
}

