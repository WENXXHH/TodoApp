package com.example.todoapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.model.TodoItem

/**
 * **`TodoDao.kt`** - 待办事项数据访问接口
 *    - 定义CRUD操作接口
 *    - 实现按时间排序查询
 *    - 添加置顶事项优先查询
 *    - 获取需要提醒的事项
 */
@Dao
interface TodoDao {
    /**
     * 插入待办事项
     * @param todoItem 待办事项
     * @return 插入的ID
     */
    @Insert
    suspend fun insert(todoItem: TodoItem): Long

    /**
     * 更新待办事项
     * @param todoItem 待办事项
     */
    @Update
    suspend fun update(todoItem: TodoItem)

    /**
     * 删除待办事项
     * @param todoItem 待办事项
     */
    @Delete
    suspend fun delete(todoItem: TodoItem)

    /**
     * 根据ID获取待办事项
     * @param id 事项ID
     * @return 待办事项
     */
    @Query("SELECT * FROM todo_items WHERE id = :id")
    suspend fun getById(id: Long): TodoItem?

    /**
     * 获取所有待办事项（置顶优先，然后按更新时间降序）
     * @return 待办事项列表
     */
    @Query("SELECT * FROM todo_items ORDER BY is_pinned DESC, updated_at DESC")
    suspend fun getAll(): List<TodoItem>

    /**
     * 获取未完成的待办事项
     * @return 待办事项列表
     */
    @Query("SELECT * FROM todo_items WHERE is_completed = 0 ORDER BY is_pinned DESC, updated_at DESC")
    suspend fun getIncomplete(): List<TodoItem>

    /**
     * 获取已完成的待办事项
     * @return 待办事项列表
     */
    @Query("SELECT * FROM todo_items WHERE is_completed = 1 ORDER BY updated_at DESC")
    suspend fun getCompleted(): List<TodoItem>

    /**
     * 根据分类ID获取待办事项
     * @param categoryId 分类ID
     * @return 待办事项列表
     */
    @Query("SELECT * FROM todo_items WHERE category_id = :categoryId ORDER BY is_pinned DESC, updated_at DESC")
    suspend fun getByCategory(categoryId: Long): List<TodoItem>

    /**
     * 获取需要提醒的待办事项
     * @param currentTime 当前时间
     * @return 待办事项列表
     */
    @Query("SELECT * FROM todo_items WHERE reminder_time > 0 AND reminder_time <= :currentTime AND is_completed = 0")
    suspend fun getReminders(currentTime: Long): List<TodoItem>

    /**
     * 搜索待办事项
     * @param keyword 搜索关键词
     * @return 待办事项列表
     */
    @Query("SELECT * FROM todo_items WHERE title LIKE :keyword OR description LIKE :keyword ORDER BY is_pinned DESC, updated_at DESC")
    suspend fun search(keyword: String): List<TodoItem>

    /**
     * 获取过期的待办事项
     * @param currentTime 当前时间
     * @return 待办事项列表
     */
    @Query("SELECT * FROM todo_items WHERE due_time > 0 AND due_time < :currentTime AND is_completed = 0")
    suspend fun getOverdue(currentTime: Long): List<TodoItem>

    /**
     * 根据用户ID获取待办事项
     * @param userId 用户ID
     * @return 待办事项列表
     */
    @Query("SELECT * FROM todo_items WHERE user_id = :userId ORDER BY is_pinned DESC, updated_at DESC")
    suspend fun getByUserId(userId: Long): List<TodoItem>
}

