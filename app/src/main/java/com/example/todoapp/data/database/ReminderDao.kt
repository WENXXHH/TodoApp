package com.example.todoapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.model.Reminder

/**
 * **`ReminderDao.kt`** - 提醒数据访问接口
 *    - 定义提醒CRUD操作接口
 *    - 实现按时间和状态查询
 */
@Dao
interface ReminderDao {

    /**
     * 插入提醒
     * @param reminder 提醒
     * @return 插入的ID
     */
    @Insert
    suspend fun insert(reminder: Reminder): Long

    /**
     * 更新提醒
     * @param reminder 提醒
     */
    @Update
    suspend fun update(reminder: Reminder)

    /**
     * 删除提醒
     * @param reminder 提醒
     */
    @Delete
    suspend fun delete(reminder: Reminder)

    /**
     * 根据ID获取提醒
     * @param id 提醒ID
     * @return 提醒
     */
    @Query("SELECT * FROM reminders WHERE id = :id")
    suspend fun getById(id: Long): Reminder?

    /**
     * 根据待办事项ID获取提醒
     * @param todoId 待办事项ID
     * @return 提醒
     */
    @Query("SELECT * FROM reminders WHERE todo_id = :todoId")
    suspend fun getByTodoId(todoId: Long): Reminder?

    /**
     * 获取所有提醒
     * @return 提醒列表
     */
    @Query("SELECT * FROM reminders ORDER BY reminder_time ASC")
    suspend fun getAll(): List<Reminder>

    /**
     * 获取未触发的提醒
     * @param currentTime 当前时间
     * @return 提醒列表
     */
    @Query("SELECT * FROM reminders WHERE status = 0 AND reminder_time <= :currentTime")
    suspend fun getPendingReminders(currentTime: Long): List<Reminder>

    /**
     * 获取已触发的提醒
     * @return 提醒列表
     */
    @Query("SELECT * FROM reminders WHERE status = 1 ORDER BY reminder_time DESC")
    suspend fun getTriggeredReminders(): List<Reminder>

    /**
     * 获取已取消的提醒
     * @return 提醒列表
     */
    @Query("SELECT * FROM reminders WHERE status = 2 ORDER BY updated_at DESC")
    suspend fun getCancelledReminders(): List<Reminder>

    /**
     * 删除指定待办事项的提醒
     * @param todoId 待办事项ID
     */
    @Query("DELETE FROM reminders WHERE todo_id = :todoId")
    suspend fun deleteByTodoId(todoId: Long)
}
