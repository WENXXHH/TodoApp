package com.example.todoapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.example.todoapp.R
import com.example.todoapp.base.BaseFragment
import com.example.todoapp.utils.PreferenceManager

/**
 * **`SettingsFragment.kt`** - 设置Fragment逻辑
 *    - 管理应用设置
 *    - 处理通知设置
 *    - 提供应用信息
 */
class SettingsFragment : BaseFragment() {

    // UI组件
    private lateinit var swNotifications: Switch
    private lateinit var swReminders: Switch
    private lateinit var tvVersion: TextView

    // 偏好管理
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // 初始化PreferenceManager
        preferenceManager = PreferenceManager(requireContext())

        // 初始化UI组件
        initViews(view)

        // 设置点击监听
        setupListeners()

        // 加载设置
        loadSettings()

        return view
    }

    /**
     * 初始化UI组件
     */
    private fun initViews(view: View) {
        swNotifications = view.findViewById(R.id.sw_notifications)
        swReminders = view.findViewById(R.id.sw_reminders)
        tvVersion = view.findViewById(R.id.tv_version)
    }

    /**
     * 设置点击监听
     */
    private fun setupListeners() {
        // 通知开关
        swNotifications.setOnCheckedChangeListener { _, isChecked ->
            preferenceManager.saveNotificationEnabled(isChecked)
            showShortToast(if (isChecked) "通知已开启" else "通知已关闭")
        }

        // 提醒开关
        swReminders.setOnCheckedChangeListener { _, isChecked ->
            preferenceManager.saveReminderEnabled(isChecked)
            showShortToast(if (isChecked) "提醒已开启" else "提醒已关闭")
        }
    }

    /**
     * 加载设置
     */
    private fun loadSettings() {
        // 加载通知设置
        swNotifications.isChecked = preferenceManager.isNotificationEnabled()

        // 加载提醒设置
        swReminders.isChecked = preferenceManager.isReminderEnabled()

        // 显示版本信息
        tvVersion.text = "版本 1.0.0"
    }
}

