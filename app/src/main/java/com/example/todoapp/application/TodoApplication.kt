package com.example.todoapp.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.room.Room
import com.example.todoapp.data.database.AppDatabase

/**
 * 应用程序全局类，负责初始化应用级别的组件和服务
 * 继承自Application类，在应用启动时自动创建实例
 */
class TodoApplication : Application() {

    // 数据库实例，使用伴生对象实现单例模式
    companion object {
        // 数据库实例，静态变量存储
        lateinit var database: AppDatabase
            private set
        
        // 应用上下文，方便在非Activity类中获取
        lateinit var appContext: Context
            private set
    }

    /**
     * 应用创建时调用的方法，执行全局初始化操作
     * 这是应用启动的第一个生命周期方法，在所有Activity、Service等组件创建之前执行
     */
    override fun onCreate() {
        super.onCreate()
        
        // 初始化应用上下文
        appContext = applicationContext
        
        // 初始化Room数据库
        initDatabase()
        
        // 创建通知渠道（Android 8.0+）
        createNotificationChannels()
        
        // 设置全局异常处理
        setupGlobalExceptionHandler()
        
        // 初始化第三方库（如果需要）
        initThirdPartyLibraries()
    }

    /**
     * 初始化Room数据库实例
     * 使用Room.databaseBuilder创建单例数据库实例
     * fallbackToDestructiveMigration()在数据库版本更新时会销毁旧数据并重建
     * 实际生产环境中应使用迁移策略
     */
    private fun initDatabase() {
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "todo_app_database"
        )
            .fallbackToDestructiveMigration() // 数据库版本更新时销毁旧数据
            .build()
    }

    /**
     * 创建通知渠道
     * Android 8.0（API 26）及以上版本需要创建通知渠道
     * 通知渠道用于对通知进行分类和管理
     */
    private fun createNotificationChannels() {
        // 检查Android版本是否为8.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 获取通知管理器
            val notificationManager = getSystemService(NotificationManager::class.java)
            
            // 创建提醒通知渠道
            val reminderChannel = NotificationChannel(
                "reminder_channel", // 渠道ID
                "待办事项提醒", // 渠道名称
                NotificationManager.IMPORTANCE_DEFAULT // 通知重要性
            ).apply {
                description = "用于显示待办事项的提醒通知"
            }
            
            // 注册通知渠道
            notificationManager.createNotificationChannel(reminderChannel)
        }
    }

    /**
     * 设置全局异常处理
     * 捕获未处理的异常，防止应用崩溃
     * 实际生产环境中应将异常信息上传到服务器进行分析
     */
    private fun setupGlobalExceptionHandler() {
        // 设置默认未捕获异常处理器
        Thread.setDefaultUncaughtExceptionHandler {
            thread, throwable ->
            // 打印异常信息
            throwable.printStackTrace()
            
            // 可以在这里添加异常日志记录逻辑
            // 例如：Log.e("TodoApp", "Uncaught exception", throwable)
            
            // 可以在这里添加崩溃后重启应用的逻辑
            // 但一般不建议这样做，应该让应用正常崩溃以便开发者分析
        }
    }

    /**
     * 初始化第三方库
     * 如果应用使用了第三方库，可以在这里进行初始化
     * 例如：网络库、图片加载库、统计分析库等
     */
    private fun initThirdPartyLibraries() {
        // 示例：初始化网络库
        // OkHttpClient.Builder().build()
        
        // 示例：初始化图片加载库
        // Glide.with(this).setDefaultRequestOptions(...)  
        
        // 示例：初始化统计分析库
        // Analytics.init(this)
    }
}


