package com.example.todoapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * **`TodoItem.kt`** - 待办事项数据模型
 *    - 定义事项核心字段
 *    - 实现状态管理（完成/未完成）
 *    - 提供时间格式化方法
 *    - 支持置顶功能标记
 */
@Entity(tableName = "todo_items")
class TodoItem {
    /**
     * 事项ID，自增长主键
     */
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    /**
     * 事项标题
     */
    var title: String = ""

    /**
     * 事项描述
     */
    var description: String = ""

    /**
     * 完成状态
     */
    @ColumnInfo(name = "is_completed")
    var isCompleted: Boolean = false

    /**
     * 截止时间（毫秒）
     */
    @ColumnInfo(name = "due_time")
    var dueTime: Long = 0

    /**
     * 创建时间（毫秒）
     */
    @ColumnInfo(name = "created_at")
    var createdAt: Long = System.currentTimeMillis()

    /**
     * 更新时间（毫秒）
     */
    @ColumnInfo(name = "updated_at")
    var updatedAt: Long = System.currentTimeMillis()

    /**
     * 置顶标记
     */
    @ColumnInfo(name = "is_pinned")
    var isPinned: Boolean = false

    /**
     * 分类ID
     */
    @ColumnInfo(name = "category_id")
    var categoryId: Long = 0

    /**
     * 提醒时间（毫秒）
     */
    @ColumnInfo(name = "reminder_time")
    var reminderTime: Long = 0

    /**
     * 用户ID
     */
    @ColumnInfo(name = "user_id")
    var userId: Long = 0

    /**
     * 检查事项数据是否有效
     * @return 是否有效
     */
    fun isValid(): Boolean {
        return title.isNotBlank()
    }

    /**
     * 切换完成状态
     */
    fun toggleCompleted() {
        isCompleted = !isCompleted
        updatedAt = System.currentTimeMillis()
    }

    /**
     * 切换置顶状态
     */
    fun togglePinned() {
        isPinned = !isPinned
        updatedAt = System.currentTimeMillis()
    }
}

