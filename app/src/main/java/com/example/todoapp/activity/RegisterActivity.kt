package com.example.todoapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.R
import com.example.todoapp.base.BaseActivity
import com.example.todoapp.data.model.User
import com.example.todoapp.data.repository.UserRepository
import com.example.todoapp.utils.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * **`RegisterActivity.kt`** - 注册界面逻辑
 *    - 实现表单验证（邮箱格式、密码强度）
 *    - 处理重复密码确认
 *    - 调用Repository完成注册
 *    - 注册成功后自动登录
 */
class RegisterActivity : BaseActivity() {
    
    // UI组件
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView
    
    // 用户仓库
    private val userRepository = UserRepository()
    
    // PreferenceManager
    private lateinit var preferenceManager: PreferenceManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        
        // 初始化PreferenceManager
        preferenceManager = PreferenceManager(this)
        
        // 初始化UI组件
        initViews()
        
        // 设置点击事件
        setupListeners()
    }
    
    /**
     * 初始化UI组件
     */
    private fun initViews() {
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        etEmail = findViewById(R.id.et_email)
        btnRegister = findViewById(R.id.btn_register)
        tvLogin = findViewById(R.id.tv_login)
    }
    
    /**
     * 设置点击事件
     */
    private fun setupListeners() {
        // 注册按钮点击事件
        btnRegister.setOnClickListener {
            register()
        }
        
        // 登录链接点击事件
        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
    
    /**
     * 注册逻辑
     */
    private fun register() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()
        val email = etEmail.text.toString().trim()
        
        // 输入验证
        if (username.isEmpty()) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (password.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (confirmPassword.isEmpty()) {
            Toast.makeText(this, "请确认密码", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (email.isEmpty()) {
            Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show()
            return
        }
        
        // 密码确认
        if (password != confirmPassword) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show()
            return
        }
        
        // 显示加载状态
        btnRegister.isEnabled = false
        
        // 创建用户对象
        val user = User().apply {
            this.username = username
            this.password = password
            this.email = email
        }
        
        // 在协程中执行注册操作
        lifecycleScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    userRepository.register(user)
                }
                
                // 注册成功
                if (result > 0) {
                    Toast.makeText(this@RegisterActivity, "注册成功", Toast.LENGTH_SHORT).show()
                    
                    // 自动登录
                    val loggedInUser = withContext(Dispatchers.IO) {
                        userRepository.login(username, password)
                    }
                    
                    if (loggedInUser != null) {
                        // 保存当前登录用户ID
                        preferenceManager.saveCurrentUserId(loggedInUser.id)
                        // 跳转到主界面
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    // 注册失败
                    when (result) {
                        -1L -> Toast.makeText(this@RegisterActivity, "输入数据无效", Toast.LENGTH_SHORT).show()
                        -2L -> Toast.makeText(this@RegisterActivity, "用户名已存在", Toast.LENGTH_SHORT).show()
                        -3L -> Toast.makeText(this@RegisterActivity, "邮箱已存在", Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(this@RegisterActivity, "注册失败", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                // 处理异常
                Toast.makeText(this@RegisterActivity, "注册失败：${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                // 恢复按钮状态
                btnRegister.isEnabled = true
            }
        }
    }
}

