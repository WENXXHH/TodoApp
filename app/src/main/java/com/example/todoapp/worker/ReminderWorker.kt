package com.example.todoapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.utils.NotificationHelper

/**
 * **`ReminderWorker.kt`** - 定时提醒任务
 *    - 使用WorkManager调度
 *    - 定期检查提醒事项
 *    - 处理失败重试逻辑
 *    - 适配不同Android版本
 */
class ReminderWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    /**
     * TodoRepository实例
     */
    private val todoRepository = TodoRepository()

    /**
     * NotificationHelper实例
     */
    private val notificationHelper = NotificationHelper(context)

    /**
     * 执行工作
     * @return 工作结果
     */
    override suspend fun doWork(): Result {
        return try {
            // 检查并发送提醒
            checkAndSendReminders()
            
            // 返回成功
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            
            // 如果是临时错误，重试
            Result.retry()
        }
    }

    /**
     * 检查并发送提醒
     */
    private suspend fun checkAndSendReminders() {
        // 获取当前需要提醒的待办事项
        val reminders = todoRepository.getReminders()
        
        // 对每个待办事项发送通知
        for (todoItem in reminders) {
            notificationHelper.sendReminderNotification(todoItem)
            
            // 重置提醒时间，避免重复提醒
            // 这里我们可以选择是否重置，或者将提醒时间设置为0
            // 暂时不重置，避免用户没有处理完的情况
        }
    }

    companion object {
        /**
         * Worker的唯一名称
         */
        const val UNIQUE_WORK_NAME = "reminder_work"
    }
}
