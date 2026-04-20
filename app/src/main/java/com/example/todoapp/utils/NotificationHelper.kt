package com.example.todoapp.utils

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.todoapp.R
import com.example.todoapp.activity.MainActivity
import com.example.todoapp.data.model.TodoItem

/**
 * **`NotificationHelper.kt`** - 通知管理工具类
 *    - 创建通知渠道
 *    - 构建提醒通知
 *    - 处理通知点击跳转
 *    - 管理通知ID
 */
class NotificationHelper(private val context: Context) {

    /**
     * 通知渠道ID，与TodoApplication中创建的保持一致
     */
    companion object {
        const val REMINDER_CHANNEL_ID = "reminder_channel"
        
        /**
         * 通知ID基础值，每个待办事项使用不同的ID
         */
        private const val BASE_NOTIFICATION_ID = 1000
    }

    /**
     * 获取通知管理器
     */
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    /**
     * 发送待办事项提醒通知
     * @param todoItem 待办事项
     */
    fun sendReminderNotification(todoItem: TodoItem) {
        // 构建通知点击后跳转到MainActivity
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // 创建PendingIntent
        val pendingIntent = PendingIntent.getActivity(
            context,
            todoItem.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 构建通知
        val notification = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_check)
            .setContentTitle("待办事项提醒")
            .setContentText(todoItem.title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(
                if (todoItem.description.isNotEmpty()) todoItem.title + ": " + todoItem.description
                else todoItem.title
            ))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // 点击后自动取消通知
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        // 发送通知，使用待办事项ID作为通知ID
        notificationManager.notify(BASE_NOTIFICATION_ID + todoItem.id.toInt(), notification)
    }

    /**
     * 取消指定ID的通知
     * @param todoItem 待办事项
     */
    fun cancelNotification(todoItem: TodoItem) {
        notificationManager.cancel(BASE_NOTIFICATION_ID + todoItem.id.toInt())
    }

    /**
     * 取消所有提醒通知
     */
    fun cancelAllNotifications() {
        notificationManager.cancelAll()
    }
}

