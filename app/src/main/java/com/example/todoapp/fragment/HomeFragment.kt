package com.example.todoapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.activity.AddTodoActivity
import com.example.todoapp.adapter.TodoAdapter
import com.example.todoapp.base.BaseFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * **`HomeFragment.kt`** - 首页Fragment逻辑
 *    - 初始化RecyclerView适配器
 *    - 加载待办事项数据
 *    - 处理添加事项点击
 *    - 监听数据变化更新UI
 */
class HomeFragment : BaseFragment() {

    // UI组件
    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var emptyView: LinearLayout

    // 适配器
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        
        // 初始化UI组件
        initViews(view)
        
        // 初始化适配器
        initAdapter()
        
        // 设置点击事件
        setupListeners()
        
        // 显示空数据提示
        showEmptyView()
        
        return view
    }

    /**
     * 初始化UI组件
     */
    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        fabAdd = view.findViewById(R.id.fab_add)
        emptyView = view.findViewById(R.id.empty_view)
    }

    /**
     * 初始化适配器
     */
    private fun initAdapter() {
        todoAdapter = TodoAdapter()
        recyclerView.adapter = todoAdapter
    }

    /**
     * 设置点击事件
     */
    private fun setupListeners() {
        // 添加按钮点击
        fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), AddTodoActivity::class.java))
        }
    }

    /**
     * 显示空数据提示
     */
    private fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }
}


