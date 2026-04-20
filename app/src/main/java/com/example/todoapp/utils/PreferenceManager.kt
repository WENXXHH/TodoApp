package com.example.todoapp.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * **`PreferenceManager.kt`** - SharedPreferences封装管理
 *    - 存储当前登录用户ID
 *    - 管理应用设置
 *    - 提供数据持久化功能
 */
class PreferenceManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("todo_app", Context.MODE_PRIVATE)

    /**
     * 存储当前登录用户ID
     * @param userId 用户ID
     */
    fun saveCurrentUserId(userId: Long) {
        sharedPreferences.edit().putLong("current_user_id", userId).apply()
    }

    /**
     * 获取当前登录用户ID
     * @return 用户ID，如果未登录返回-1
     */
    fun getCurrentUserId(): Long {
        return sharedPreferences.getLong("current_user_id", -1)
    }

    /**
     * 清除当前登录用户信息（退出登录）
     */
    fun clearCurrentUser() {
        sharedPreferences.edit().remove("current_user_id").apply()
    }

    /**
     * 检查用户是否已登录
     * @return 是否已登录
     */
    fun isLoggedIn(): Boolean {
        return getCurrentUserId() != -1L
    }
}
