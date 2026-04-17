package com.example.todoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.data.model.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun userDao(): UserDao
    
    companion object {
        // 数据库实例，静态变量存储
        lateinit var database: AppDatabase
            private set
        
        /**
         * 初始化数据库
         * @param context 应用上下文
         */
        fun init(context: Context) {
            database = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "todo_app_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
