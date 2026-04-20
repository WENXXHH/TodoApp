package com.example.todoapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.R
import com.example.todoapp.base.BaseActivity
import com.example.todoapp.data.repository.UserRepository
import com.example.todoapp.utils.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * **`ProfileActivity.kt`** - 用户信息逻辑
 *    - 加载当前用户信息
 *    - 处理头像上传
 *    - 支持修改个人信息
 *    - 实现退出登录功能
 */
class ProfileActivity : BaseActivity() {

    // UI组件
    private lateinit var toolbar: Toolbar
    private lateinit var ivAvatar: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvChangeAvatar: TextView
    private lateinit var editProfileContainer: android.view.View
    private lateinit var settingsContainer: android.view.View
    private lateinit var aboutContainer: android.view.View
    private lateinit var btnLogout: Button

    // 数据
    private val userRepository = UserRepository()
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // 初始化PreferenceManager
        preferenceManager = PreferenceManager(this)

        // 初始化UI组件
        initViews()

        // 设置工具栏
        setupToolbar()

        // 设置点击监听
        setupListeners()

        // 加载用户信息
        loadUserInfo()
    }

    /**
     * 初始化UI组件
     */
    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        ivAvatar = findViewById(R.id.iv_avatar)
        tvUsername = findViewById(R.id.tv_username)
        tvEmail = findViewById(R.id.tv_email)
        tvChangeAvatar = findViewById(R.id.tv_change_avatar)
        editProfileContainer = findViewById(R.id.edit_profile_container)
        settingsContainer = findViewById(R.id.settings_container)
        aboutContainer = findViewById(R.id.about_container)
        btnLogout = findViewById(R.id.btn_logout)
    }

    /**
     * 设置工具栏
     */
    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    /**
     * 设置点击监听
     */
    private fun setupListeners() {
        // 更换头像
        tvChangeAvatar.setOnClickListener {
            Toast.makeText(this, "头像上传功能待实现", Toast.LENGTH_SHORT).show()
        }

        // 编辑个人信息
        editProfileContainer.setOnClickListener {
            Toast.makeText(this, "编辑个人信息功能待实现", Toast.LENGTH_SHORT).show()
        }

        // 设置
        settingsContainer.setOnClickListener {
            Toast.makeText(this, "设置功能待实现", Toast.LENGTH_SHORT).show()
        }

        // 关于
        aboutContainer.setOnClickListener {
            Toast.makeText(this, "关于功能待实现", Toast.LENGTH_SHORT).show()
        }

        // 退出登录
        btnLogout.setOnClickListener {
            logout()
        }
    }

    /**
     * 加载用户信息
     */
    private fun loadUserInfo() {
        val userId = preferenceManager.getCurrentUserId()
        if (userId == -1L) {
            // 没有登录，跳转到登录页面
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        lifecycleScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    userRepository.getUserById(userId)
                }

                if (user != null) {
                    tvUsername.text = user.username
                    tvEmail.text = user.email
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "加载用户信息失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 退出登录
     */
    private fun logout() {
        // 清除登录状态
        preferenceManager.clearCurrentUser()

        // 跳转到登录页面
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
