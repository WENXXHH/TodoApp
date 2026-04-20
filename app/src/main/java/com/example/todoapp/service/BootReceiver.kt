package com.example.todoapp.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todoapp.worker.ReminderWorker
import java.util.concurrent.TimeUnit

/**
 * **`BootReceiver.kt`** - 开机启动接收器
 *    - 监听系统启动完成广播
 *    - 重新调度提醒任务
 *    - 恢复后台服务
 *    - 处理权限检查
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // 检查是否是开机启动完成广播
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // 重新调度WorkManager提醒任务
            scheduleReminderWorker(context)
            
            // 也可以启动ReminderService作为补充
            startReminderService(context)
        }
    }

    /**
     * 调度WorkManager提醒任务
     */
    private fun scheduleReminderWorker(context: Context) {
        // 创建定期工作请求，每30分钟检查一次
        val periodicWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            30, // 重复间隔
            TimeUnit.MINUTES // 时间单位
        ).build()

        // 调度工作，使用现有策略，避免重复调度
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            ReminderWorker.UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    /**
     * 启动提醒服务
     */
    private fun startReminderService(context: Context) {
        try {
            val serviceIntent = Intent(context, ReminderService::class.java)
            context.startService(serviceIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

