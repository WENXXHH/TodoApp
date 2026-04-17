package com.example.todoapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.model.User

/**
 * **`UserDao.kt`** - 用户数据访问接口
 *    - 定义用户增删改查操作
 *    - 实现登录验证查询
 *    - 用户名唯一性检查
 */
@Dao
interface UserDao {
    /**
     * 插入新用户
     * @param user 用户对象
     * @return 插入的用户ID
     */
    @Insert
    suspend fun insert(user: User): Long
    
    /**
     * 更新用户信息
     * @param user 用户对象
     */
    @Update
    suspend fun update(user: User)
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户对象
     */
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Long): User?
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象
     */
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?
    
    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 用户对象
     */
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?
    
    /**
     * 登录验证查询
     * @param username 用户名
     * @param password 密码
     * @return 用户对象，如果验证失败返回null
     */
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): User?
    
    /**
     * 检查用户名是否已存在
     * @param username 用户名
     * @return 存在返回1，不存在返回0
     */
    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    suspend fun isUsernameExists(username: String): Int
    
    /**
     * 检查邮箱是否已存在
     * @param email 邮箱
     * @return 存在返回1，不存在返回0
     */
    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun isEmailExists(email: String): Int
    
    /**
     * 删除用户
     * @param id 用户ID
     */
    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteById(id: Long)
}

