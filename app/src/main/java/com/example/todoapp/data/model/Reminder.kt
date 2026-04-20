package com.example.todoapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * **`Reminder.kt`** - 提醒数据模型
 *    - 定义提醒核心字段
 *    - 支持不同类型的提醒
 *    - 提供提醒状态管理
 */
@Entity(tableName = "reminders")
class Reminder {

    /**
     * 提醒ID，自增长主键
     */
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    /**
     * 待办事项ID
     */
    @ColumnInfo(name = "todo_id")
    var todoId: Long = 0

    /**
     * 提醒时间（毫秒）
     */
    @ColumnInfo(name = "reminder_time")
    var reminderTime: Long = 0

    /**
     * 提醒类型
     * 0: 一次性提醒
     * 1: 每天提醒
     * 2: 每周提醒
     * 3: 每月提醒
     */
    @ColumnInfo(name = "reminder_type")
    var reminderType: Int = 0

    /**
     * 提醒状态
     * 0: 未触发
     * 1: 已触发
     * 2: 已取消
     */
    @ColumnInfo(name = "status")
    var status: Int = 0

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
     * 检查提醒数据是否有效
     * @return 是否有效
     */
    fun isValid(): Boolean {
        return todoId > 0 && reminderTime > 0
    }

    /**
     * 触发提醒
     */
    fun trigger() {
        status = 1
        updatedAt = System.currentTimeMillis()
    }

    /**
     * 取消提醒
     */
    fun cancel() {
        status = 2
        updatedAt = System.currentTimeMillis()
    }

    /**
     * 重置提醒
     */
    fun reset() {
        status = 0
        updatedAt = System.currentTimeMillis()
    }
}

