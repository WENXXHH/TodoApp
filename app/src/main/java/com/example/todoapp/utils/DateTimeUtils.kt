package com.example.todoapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * **`DateTimeUtils.kt`** - 日期时间格式化工具
 *    - 格式化日期显示
 *    - 计算相对时间
 *    - 处理时区转换
 *    - 优化时间显示
 */
object DateTimeUtils {

    /**
     * 日期时间格式
     */
    private val DATE_TIME_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    /**
     * 日期格式
     */
    private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    /**
     * 时间格式
     */
    private val TIME_FORMAT = SimpleDateFormat("HH:mm", Locale.getDefault())

    /**
     * 格式化日期时间
     * @param timestamp 时间戳
     * @return 格式化后的字符串
     */
    fun formatDateTime(timestamp: Long): String {
        return DATE_TIME_FORMAT.format(Date(timestamp))
    }

    /**
     * 格式化日期
     * @param timestamp 时间戳
     * @return 格式化后的字符串
     */
    fun formatDate(timestamp: Long): String {
        return DATE_FORMAT.format(Date(timestamp))
    }

    /**
     * 格式化时间
     * @param timestamp 时间戳
     * @return 格式化后的字符串
     */
    fun formatTime(timestamp: Long): String {
        return TIME_FORMAT.format(Date(timestamp))
    }

    /**
     * 获取相对时间描述
     * @param timestamp 时间戳
     * @return 相对时间描述
     */
    fun getRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        
        return when {
            days > 0 -> "${days}天前"
            hours > 0 -> "${hours}小时前"
            minutes > 0 -> "${minutes}分钟前"
            else -> "刚刚"
        }
    }

    /**
     * 检查是否是今天
     * @param timestamp 时间戳
     * @return 是否是今天
     */
    fun isToday(timestamp: Long): Boolean {
        val today = DATE_FORMAT.format(Date())
        val targetDate = DATE_FORMAT.format(Date(timestamp))
        return today == targetDate
    }

    /**
     * 检查是否是明天
     * @param timestamp 时间戳
     * @return 是否是明天
     */
    fun isTomorrow(timestamp: Long): Boolean {
        val tomorrow = DATE_FORMAT.format(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
        val targetDate = DATE_FORMAT.format(Date(timestamp))
        return tomorrow == targetDate
    }

    /**
     * 检查是否已过期
     * @param timestamp 时间戳
     * @return 是否已过期
     */
    fun isOverdue(timestamp: Long): Boolean {
        return timestamp > 0 && timestamp < System.currentTimeMillis()
    }

    /**
     * 获取剩余时间描述
     * @param timestamp 时间戳
     * @return 剩余时间描述
     */
    fun getRemainingTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = timestamp - now
        
        if (diff <= 0) {
            return "已过期"
        }
        
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        
        return when {
            days > 0 -> "剩余${days}天"
            hours > 0 -> "剩余${hours}小时"
            minutes > 0 -> "剩余${minutes}分钟"
            else -> "即将到期"
        }
    }
}

