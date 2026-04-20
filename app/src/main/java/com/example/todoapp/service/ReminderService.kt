package com.example.todoapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.utils.NotificationHelper

/**
 * **`ReminderService.kt`** - 后台提醒服务
 *    - 检查当前需要提醒的事项
 *    - 调用NotificationHelper发送通知
 *    - 处理通知点击Intent
 *    - 管理服务生命周期
 */
class ReminderService : Service() {

    /**
     * 协程作用域
     */
    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())

    /**
     * TodoRepository实例
     */
    private lateinit var todoRepository: TodoRepository

    /**
     * NotificationHelper实例
     */
    private lateinit var notificationHelper: NotificationHelper

    /**
     * 检查提醒的间隔时间（毫秒），每15分钟检查一次
     */
    private val CHECK_INTERVAL = 15 * 60 * 1000L

    /**
     * 服务创建时调用，初始化组件
     */
    override fun onCreate() {
        super.onCreate()
        
        // 初始化TodoRepository
        todoRepository = TodoRepository()
        
        // 初始化NotificationHelper
        notificationHelper = NotificationHelper(this)
        
        // 开始检查提醒
        startReminderCheck()
    }

    /**
     * 服务绑定，这里我们不使用绑定
     */
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    /**
     * 服务销毁时调用
     */
    override fun onDestroy() {
        super.onDestroy()
        // 取消所有通知
        notificationHelper.cancelAllNotifications()
        super.onDestroy()
    }

    /**
     * 开始检查提醒
     */
    private fun startReminderCheck() {
        serviceScope.launch {
            while (true) {
                // 检查并发送提醒
                checkAndSendReminders()
                
                // 等待指定间隔
                delay(CHECK_INTERVAL)
            }
        }
    }

    /**
     * 检查并发送提醒
     */
    private suspend fun checkAndSendReminders() {
        try {
            // 获取当前需要提醒的待办事项
            val reminders = todoRepository.getReminders()
            
            // 对每个待办事项发送通知
            for (todoItem in reminders) {
                notificationHelper.sendReminderNotification(todoItem)
                
                // 重置提醒时间，避免重复提醒
                // 这里我们可以选择是否重置，或者将提醒时间设置为0
                // 暂时不重置，避免用户没有处理完的情况
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

