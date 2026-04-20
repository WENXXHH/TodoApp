package com.example.todoapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todoapp.R
import com.example.todoapp.base.BaseActivity
import com.example.todoapp.fragment.CategoryFragment
import com.example.todoapp.fragment.HomeFragment
import com.example.todoapp.fragment.ProfileFragment
import com.example.todoapp.service.ReminderService
import com.example.todoapp.worker.ReminderWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.activity.addCallback
import java.util.concurrent.TimeUnit

/**
 * **`MainActivity.kt`** - 主界面逻辑
 *    - 初始化底部导航
 *    - 管理Fragment切换
 *    - 处理返回键逻辑
 *    - 检查登录状态
 */
class MainActivity : BaseActivity() {

    // UI组件
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var toolbar: Toolbar

    // Fragment实例
    private lateinit var homeFragment: HomeFragment
    private lateinit var categoryFragment: CategoryFragment
    private lateinit var profileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化UI组件
        initViews()

        // 初始化Fragment
        initFragments()

        // 设置底部导航监听
        setupBottomNavigation()

        // 设置工具栏
        setupToolbar()

        // 默认显示首页
        showFragment(homeFragment)

        // 设置返回键处理
        setupBackHandler()

        // 启动提醒服务和调度WorkManager任务
        startReminderService()
        scheduleReminderWorker()
    }

    /**
     * 初始化UI组件
     */
    private fun initViews() {
        bottomNavigation = findViewById(R.id.bottom_navigation)
        fragmentContainer = findViewById(R.id.fragment_container)
        toolbar = findViewById(R.id.toolbar)
    }

    /**
     * 初始化Fragment
     */
    private fun initFragments() {
        homeFragment = HomeFragment()
        categoryFragment = CategoryFragment()
        profileFragment = ProfileFragment()
    }

    /**
     * 设置工具栏
     */
    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    /**
     * 设置底部导航监听
     */
    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    showFragment(homeFragment)
                    toolbar.title = "待办事项"
                    true
                }
                R.id.nav_categories -> {
                    showFragment(categoryFragment)
                    toolbar.title = "分类管理"
                    true
                }
                R.id.nav_profile -> {
                    showFragment(profileFragment)
                    toolbar.title = "个人中心"
                    true
                }
                else -> false
            }
        }
    }

    /**
     * 显示指定的Fragment
     * @param fragment 要显示的Fragment
     */
    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    /**
     * 设置返回键处理
     */
    private fun setupBackHandler() {
        onBackPressedDispatcher.addCallback(this as LifecycleOwner) {
            // 如果当前不是首页，则返回首页
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFragment != homeFragment) {
                showFragment(homeFragment)
                bottomNavigation.selectedItemId = R.id.nav_home
                toolbar.title = "待办事项"
            } else {
                // 如果是首页，则退出应用
                finish()
            }
        }
    }

    /**
     * 创建菜单
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    /**
     * 菜单点击处理
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                // 处理搜索
                return true
            }
            R.id.action_settings -> {
                // 处理设置
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * 启动提醒服务
     */
    private fun startReminderService() {
        try {
            val intent = Intent(this, ReminderService::class.java)
            startService(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 调度WorkManager提醒任务
     */
    private fun scheduleReminderWorker() {
        try {
            // 创建定期工作请求，每30分钟检查一次
            val periodicWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
                30, // 重复间隔
                TimeUnit.MINUTES // 时间单位
            ).build()

            // 调度工作，使用现有策略，避免重复调度
            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                ReminderWorker.UNIQUE_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

