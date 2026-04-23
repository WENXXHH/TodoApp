package com.example.todoapp.data.repository

import com.example.todoapp.data.database.AppDatabase
import com.example.todoapp.data.database.ReminderDao
import com.example.todoapp.data.model.Reminder

/**
 * **`ReminderRepository.kt`** - 提醒业务逻辑封装
 *    - 封装提醒数据操作
 *    - 处理提醒状态更新
 *    - 管理提醒的增删改查
 */
class ReminderRepository(private val reminderDao: ReminderDao) {

    /**
     * 构造函数，使用默认的ReminderDao实例
     */
    constructor() : this(AppDatabase.database.reminderDao())

    /**
     * 添加提醒
     * @param reminder 提醒
     * @return 添加结果，成功返回ID，失败返回-1
     */
    suspend fun addReminder(reminder: Reminder): Long {
        if (!reminder.isValid()) {
            return -1
        }
        return reminderDao.insert(reminder)
    }

    /**
     * 更新提醒
     * @param reminder 提醒
     * @return 更新是否成功
     */
    suspend fun updateReminder(reminder: Reminder): Boolean {
        if (!reminder.isValid()) {
            return false
        }
        reminder.updatedAt = System.currentTimeMillis()
        reminderDao.update(reminder)
        return true
    }

    /**
     * 删除提醒
     * @param reminder 提醒
     */
    suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.delete(reminder)
    }

    /**
     * 根据ID获取提醒
     * @param id 提醒ID
     * @return 提醒
     */
    suspend fun getReminderById(id: Long): Reminder? {
        return reminderDao.getById(id)
    }

    /**
     * 根据待办事项ID获取提醒
     * @param todoId 待办事项ID
     * @return 提醒
     */
    suspend fun getReminderByTodoId(todoId: Long): Reminder? {
        return reminderDao.getByTodoId(todoId)
    }

    /**
     * 获取所有提醒
     * @return 提醒列表
     */
    suspend fun getAllReminders(): List<Reminder> {
        return reminderDao.getAll()
    }

    /**
     * 获取待处理的提醒
     * @return 提醒列表
     */
    suspend fun getPendingReminders(): List<Reminder> {
        return reminderDao.getPendingReminders(System.currentTimeMillis())
    }

    /**
     * 触发提醒
     * @param reminder 提醒
     */
    suspend fun triggerReminder(reminder: Reminder) {
        reminder.trigger()
        reminderDao.update(reminder)
    }

    /**
     * 取消提醒
     * @param reminder 提醒
     */
    suspend fun cancelReminder(reminder: Reminder) {
        reminder.cancel()
        reminderDao.update(reminder)
    }

    /**
     * 删除指定待办事项的提醒
     * @param todoId 待办事项ID
     */
    suspend fun deleteReminderByTodoId(todoId: Long) {
        reminderDao.deleteByTodoId(todoId)
    }
}
