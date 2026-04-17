package com.example.todoapp.data.repository

import com.example.todoapp.data.database.AppDatabase
import com.example.todoapp.data.database.UserDao
import com.example.todoapp.data.model.User

/**
 * **`UserRepository.kt`** - 用户业务逻辑
 *    - 封装用户注册流程
 *    - 实现登录认证逻辑
 *    - 处理用户信息更新
 */
class UserRepository(private val userDao: UserDao) {
    
    /**
     * 构造函数，使用默认的UserDao实例
     */
    constructor() : this(AppDatabase.database.userDao())
    
    /**
     * 用户注册
     * @param user 用户对象
     * @return 注册结果，成功返回用户ID，失败返回-1
     */
    suspend fun register(user: User): Long {
        // 验证用户数据
        if (!user.isUsernameValid() || !user.isPasswordValid() || !user.isEmailValid()) {
            return -1
        }
        
        // 检查用户名是否已存在
        if (userDao.isUsernameExists(user.username) > 0) {
            return -2 // 用户名已存在
        }
        
        // 检查邮箱是否已存在
        if (userDao.isEmailExists(user.email) > 0) {
            return -3 // 邮箱已存在
        }
        
        // 密码加密（实际项目中应使用更安全的加密方式）
        // 这里为了演示，暂时不加密
        
        // 插入用户
        return userDao.insert(user)
    }
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户对象，失败返回null
     */
    suspend fun login(username: String, password: String): User? {
        val user = userDao.login(username, password)
        
        // 登录成功，更新最后登录时间
        user?.let {
            it.lastLoginAt = System.currentTimeMillis()
            userDao.update(it)
        }
        
        return user
    }
    
    /**
     * 根据ID获取用户
     * @param id 用户ID
     * @return 用户对象
     */
    suspend fun getUserById(id: Long): User? {
        return userDao.getUserById(id)
    }
    
    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户对象
     */
    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }
    
    /**
     * 更新用户信息
     * @param user 用户对象
     * @return 更新是否成功
     */
    suspend fun updateUser(user: User): Boolean {
        // 验证用户数据
        if (!user.isUsernameValid() || !user.isEmailValid()) {
            return false
        }
        
        // 检查用户名是否被其他用户使用
        val existingUser = userDao.getUserByUsername(user.username)
        if (existingUser != null && existingUser.id != user.id) {
            return false // 用户名已被其他用户使用
        }
        
        // 检查邮箱是否被其他用户使用
        val existingEmailUser = userDao.getUserByEmail(user.email)
        if (existingEmailUser != null && existingEmailUser.id != user.id) {
            return false // 邮箱已被其他用户使用
        }
        
        // 更新用户信息
        userDao.update(user)
        return true
    }
    
    /**
     * 检查用户名是否已存在
     * @param username 用户名
     * @return 是否存在
     */
    suspend fun isUsernameExists(username: String): Boolean {
        return userDao.isUsernameExists(username) > 0
    }
    
    /**
     * 检查邮箱是否已存在
     * @param email 邮箱
     * @return 是否存在
     */
    suspend fun isEmailExists(email: String): Boolean {
        return userDao.isEmailExists(email) > 0
    }
}

