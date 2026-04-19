package com.example.todoapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.activity.AddTodoActivity
import com.example.todoapp.activity.EditTodoActivity
import com.example.todoapp.adapter.TodoAdapter
import com.example.todoapp.base.BaseFragment
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.repository.TodoRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    private val todoRepository = TodoRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        
        // 初始化UI组件
        initViews(view)
        
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
                toggleTodoCompletion(todo)
            },
            onEditClick = { todo ->
                // 编辑待办事项
                val intent = Intent(requireContext(), EditTodoActivity::class.java)
                intent.putExtra("todo_id", todo.id)
                startActivity(intent)
            },
            onDeleteClick = { todo ->
                // 删除待办事项
                deleteTodo(todo)
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
        CoroutineScope(Dispatchers.IO).launch {
            val todos = todoRepository.getAllTodos()
            
            requireActivity().runOnUiThread {
                if (todos.isEmpty()) {
                    showEmptyView()
                } else {
                    showTodoList(todos)
                }
            }
        }
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
     * 切换待办事项完成状态
     */
    private fun toggleTodoCompletion(todo: TodoItem) {
        CoroutineScope(Dispatchers.IO).launch {
            todoRepository.toggleTodoCompletion(todo)
            // 重新加载数据
            loadTodos()
        }
    }

    /**
     * 删除待办事项
     */
    private fun deleteTodo(todo: TodoItem) {
        CoroutineScope(Dispatchers.IO).launch {
            todoRepository.deleteTodo(todo)
            // 重新加载数据
            loadTodos()
        }
    }

    /**
     * 当Fragment重新可见时，刷新数据
     */
    override fun onResume() {
        super.onResume()
        loadTodos()
    }
}


