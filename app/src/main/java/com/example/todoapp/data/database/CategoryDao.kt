package com.example.todoapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.model.Category

/**
 * **`CategoryDao.kt`** - 分类数据访问接口
 *    - 定义分类CRUD操作接口
 *    - 实现按名称查询
 */
@Dao
interface CategoryDao {

    /**
     * 插入分类
     * @param category 分类
     * @return 插入的ID
     */
    @Insert
    suspend fun insert(category: Category): Long

    /**
     * 更新分类
     * @param category 分类
     */
    @Update
    suspend fun update(category: Category)

    /**
     * 删除分类
     * @param category 分类
     */
    @Delete
    suspend fun delete(category: Category)

    /**
     * 根据ID获取分类
     * @param id 分类ID
     * @return 分类
     */
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getById(id: Long): Category?

    /**
     * 获取所有分类
     * @return 分类列表
     */
    @Query("SELECT * FROM categories ORDER BY updated_at DESC")
    suspend fun getAll(): List<Category>

    /**
     * 根据名称获取分类
     * @param name 分类名称
     * @return 分类
     */
    @Query("SELECT * FROM categories WHERE name = :name")
    suspend fun getByName(name: String): Category?

    /**
     * 检查分类名称是否存在
     * @param name 分类名称
     * @return 存在数量
     */
    @Query("SELECT COUNT(*) FROM categories WHERE name = :name")
    suspend fun isNameExists(name: String): Int

    /**
     * 获取分类下的待办事项数量
     * @param categoryId 分类ID
     * @return 待办事项数量
     */
    @Query("SELECT COUNT(*) FROM todo_items WHERE category_id = :categoryId")
    suspend fun getTodoCount(categoryId: Long): Int
}