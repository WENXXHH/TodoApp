package com.example.todoapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * **`Category.kt`** - 分类数据模型
 *    - 定义分类核心字段
 *    - 提供分类名称验证
 */
@Entity(tableName = "categories")
class Category {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "color")
    var color: String = "#6200EE"

    @ColumnInfo(name = "icon")
    var icon: String = ""

    @ColumnInfo(name = "created_at")
    var createdAt: Long = System.currentTimeMillis()

    @ColumnInfo(name = "updated_at")
    var updatedAt: Long = System.currentTimeMillis()

    /**
     * 验证分类名称是否有效
     * @return 是否有效
     */
    fun isNameValid(): Boolean {
        return name.isNotBlank() && name.length <= 50
    }
}