package com.example.todoapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.R
import com.example.todoapp.activity.ProfileActivity
import com.example.todoapp.base.BaseFragment
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.data.repository.UserRepository
import com.example.todoapp.utils.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * **`ProfileFragment.kt`** - 个人中心Fragment
 *    - 显示用户信息
 *    - 展示待办统计数据
 *    - 提供功能选项
 */
class ProfileFragment : BaseFragment() {

    // UI组件
    private lateinit var ivAvatar: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvViewProfile: TextView
    private lateinit var tvTotalCount: TextView
    private lateinit var tvCompletedCount: TextView
    private lateinit var tvPendingCount: TextView

    // 数据
    private val userRepository = UserRepository()
    private val todoRepository = TodoRepository()
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // 初始化PreferenceManager
        preferenceManager = PreferenceManager(requireContext())

        // 初始化UI组件
        initViews(view)

        // 设置点击监听
        setupListeners()

        return view
    }

    override fun onResume() {
        super.onResume()
        // 加载用户信息
        loadUserInfo()
        // 加载统计信息
        loadStats()
    }

    /**
     * 初始化UI组件
     */
    private fun initViews(view: View) {
        ivAvatar = view.findViewById(R.id.iv_avatar)
        tvUsername = view.findViewById(R.id.tv_username)
        tvEmail = view.findViewById(R.id.tv_email)
        tvViewProfile = view.findViewById(R.id.tv_view_profile)
        tvTotalCount = view.findViewById(R.id.tv_total_count)
        tvCompletedCount = view.findViewById(R.id.tv_completed_count)
        tvPendingCount = view.findViewById(R.id.tv_pending_count)
    }

    /**
     * 设置点击监听
     */
    private fun setupListeners() {
        tvViewProfile.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }
    }

    /**
     * 加载用户信息
     */
    private fun loadUserInfo() {
        val userId = preferenceManager.getCurrentUserId()
        if (userId == -1L) {
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
                // 忽略错误
            }
        }
    }

    /**
     * 加载统计信息
     */
    private fun loadStats() {
        val userId = preferenceManager.getCurrentUserId()
        if (userId == -1L) {
            return
        }

        lifecycleScope.launch {
            try {
                val stats = withContext(Dispatchers.IO) {
                    val allTodos = todoRepository.getTodosByUserId(userId)
                    val completedTodos = allTodos.filter { it.isCompleted }
                    val pendingTodos = allTodos.filter { !it.isCompleted }
                    Triple(allTodos.size, completedTodos.size, pendingTodos.size)
                }

                tvTotalCount.text = stats.first.toString()
                tvCompletedCount.text = stats.second.toString()
                tvPendingCount.text = stats.third.toString()
            } catch (e: Exception) {
                // 忽略错误
            }
        }
    }
}

