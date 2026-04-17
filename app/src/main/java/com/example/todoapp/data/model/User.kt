package com.example.todoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用户实体类，用于Room数据库存储
 * @Entity 注解标记为数据库表
 */
@Entity(tableName = "users")
class User {
    /**
     * 用户ID，自增长主键
     */
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    
    /**
     * 用户名，用于登录和显示
     */
    var username: String = ""
    
    /**
     * 密码，存储加密后的密码
     */
    var password: String = ""
    
    /**
     * 邮箱，用于找回密码等功能
     */
    var email: String = ""
    
    /**
     * 用户头像URL或本地路径
     */
    var avatar: String = ""
    
    /**
     * 用户注册时间
     */
    var createdAt: Long = System.currentTimeMillis()
    
    /**
     * 用户最后登录时间
     */
    var lastLoginAt: Long = 0
    
    /**
     * 验证用户名是否有效
     * @return 用户名是否有效
     */
    fun isUsernameValid(): Boolean {
        return username.isNotBlank() && username.length >= 3
    }
    
    /**
     * 验证密码是否有效
     * @return 密码是否有效
     */
    fun isPasswordValid(): Boolean {
        return password.isNotBlank() && password.length >= 6
    }
    
    /**
     * 验证邮箱是否有效
     * @return 邮箱是否有效
     */
    fun isEmailValid(): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()
        return email.matches(emailRegex)
    }
}

