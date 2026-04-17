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
import com.example.todoapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * **`LoginActivity.kt`** - 登录界面逻辑
 *    - 处理用户输入验证
 *    - 调用Repository进行认证
 *    - 成功后跳转主界面
 *    - 失败时显示错误提示
 */
class LoginActivity : BaseActivity() {
    
    // UI组件
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    
    // 用户仓库
    private val userRepository = UserRepository()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
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
        btnLogin = findViewById(R.id.btn_login)
        tvRegister = findViewById(R.id.tv_register)
    }
    
    /**
     * 设置点击事件
     */
    private fun setupListeners() {
        // 登录按钮点击事件
        btnLogin.setOnClickListener {
            login()
        }
        
        // 注册链接点击事件
        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    
    /**
     * 登录逻辑
     */
    private fun login() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        
        // 输入验证
        if (username.isEmpty()) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (password.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show()
            return
        }
        
        // 显示加载状态
        btnLogin.isEnabled = false
        
        // 在协程中执行登录操作
        lifecycleScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    userRepository.login(username, password)
                }
                
                // 登录成功
                if (user != null) {
                    Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT).show()
                    // 跳转到主界面
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    // 登录失败
                    Toast.makeText(this@LoginActivity, "用户名或密码错误", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // 处理异常
                Toast.makeText(this@LoginActivity, "登录失败：${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                // 恢复按钮状态
                btnLogin.isEnabled = true
            }
        }
    }
}

