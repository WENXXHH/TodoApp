package com.example.todoapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.activity.AddTodoActivity
import com.example.todoapp.activity.EditTodoActivity
import com.example.todoapp.adapter.TodoAdapter
import com.example.todoapp.base.BaseFragment
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.viewmodel.TodoViewModel
import com.example.todoapp.viewmodel.TodoViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collect

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

    // 适配器和数据
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        
        // 初始化UI组件
        initViews(view)
        
        // 初始化ViewModel
        initViewModel()
        
        // 初始化RecyclerView
        initRecyclerView()
        
        // 初始化适配器
        initAdapter()
        
        // 设置点击事件
        setupListeners()
        
        // 加载待办事项数据
        loadTodos()
        
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
     * 初始化ViewModel
     */
    private fun initViewModel() {
        todoViewModel = ViewModelProvider(this, TodoViewModelFactory())[TodoViewModel::class.java]
        
        // 观察UI状态变化
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            todoViewModel.uiState.collect {
                when {
                    it.isLoading -> {
                        // 显示加载状态
                        showLoading()
                    }
                    it.error != null -> {
                        // 显示错误信息
                        showError(it.error)
                    }
                    it.isEmpty -> {
                        // 显示空状态
                        showEmptyView()
                    }
                    else -> {
                        // 显示待办事项列表
                        showTodoList(it.todos)
                    }
                }
            }
        }
    }

    /**
     * 初始化RecyclerView
     */
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    /**
     * 初始化适配器
     */
    private fun initAdapter() {
        todoAdapter = TodoAdapter(
            emptyList(),
            onTodoClick = { todo ->
                // 点击待办事项
                val intent = Intent(requireContext(), EditTodoActivity::class.java)
                intent.putExtra("todo_id", todo.id)
                startActivity(intent)
            },
            onTodoLongClick = { todo ->
                // 长按待办事项
                Toast.makeText(requireContext(), "长按：${todo.title}", Toast.LENGTH_SHORT).show()
                true
            },
            onCompleteToggle = { todo ->
                // 切换完成状态
                todoViewModel.toggleCompletion(todo)
            },
            onEditClick = { todo ->
                // 编辑待办事项
                val intent = Intent(requireContext(), EditTodoActivity::class.java)
                intent.putExtra("todo_id", todo.id)
                startActivity(intent)
            },
            onDeleteClick = { todo ->
                // 删除待办事项
                todoViewModel.deleteTodo(todo)
            }
        )
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
     * 加载待办事项数据
     */
    private fun loadTodos() {
        todoViewModel.loadTodos()
    }

    /**
     * 显示加载状态
     */
    private fun showLoading() {
        // 可以添加加载动画
    }

    /**
     * 显示错误信息
     */
    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    /**
     * 显示待办事项列表
     */
    private fun showTodoList(todos: List<TodoItem>) {
        emptyView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        todoAdapter.updateTodos(todos)
    }

    /**
     * 显示空数据提示
     */
    private fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    /**
     * 当Fragment重新可见时，刷新数据
     */
    override fun onResume() {
        super.onResume()
        loadTodos()
    }
}


