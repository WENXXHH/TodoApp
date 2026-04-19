package com.example.todoapp.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.todoapp.R
import com.example.todoapp.base.BaseActivity
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.utils.DateTimeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * **`EditTodoActivity.kt`** - 编辑待办事项逻辑
 *    - 加载现有事项数据
 *    - 支持修改所有字段
 *    - 处理事项删除
 *    - 保存更新到数据库
 */
class EditTodoActivity : BaseActivity() {

    // UI组件
    private lateinit var toolbar: Toolbar
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var tvDueDate: TextView
    private lateinit var tvCategory: TextView
    private lateinit var swPin: Switch
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button

    // 数据
    private var todoId: Long = 0
    private lateinit var todo: TodoItem
    private var dueTime: Long = 0
    private var categoryId: Long = 0
    private val todoRepository = TodoRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_form)

        // 获取传递的todoId
        todoId = intent.getLongExtra("todo_id", 0L)
        if (todoId == 0L) {
            Toast.makeText(this, "无效的待办事项", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 初始化UI组件
        initViews()

        // 设置工具栏
        setupToolbar()

        // 加载待办事项数据
        loadTodoData()

        // 设置点击监听
        setupListeners()
    }

    /**
     * 初始化UI组件
     */
    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        etTitle = findViewById(R.id.et_title)
        etDescription = findViewById(R.id.et_description)
        tvDueDate = findViewById(R.id.tv_due_date)
        tvCategory = findViewById(R.id.tv_category)
        swPin = findViewById(R.id.sw_pin)
        btnSave = findViewById(R.id.btn_save)

        // 添加删除按钮
        btnDelete = Button(this).apply {
            text = "删除"
            setBackgroundColor(resources.getColor(R.color.错误))
            setTextColor(resources.getColor(R.color.white))
            layoutParams = btnSave.layoutParams
            setPadding(16, 16, 16, 16)
        }
        (btnSave.parent as android.view.ViewGroup).addView(btnDelete, btnSave.layoutParams)
    }

    /**
     * 设置工具栏
     */
    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "编辑待办事项"
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    /**
     * 加载待办事项数据
     */
    private fun loadTodoData() {
        CoroutineScope(Dispatchers.IO).launch {
            val todoItem = todoRepository.getTodoById(todoId)
            if (todoItem != null) {
                todo = todoItem
                dueTime = todo.dueTime
                categoryId = todo.categoryId
                
                runOnUiThread {
                    etTitle.setText(todo.title)
                    etDescription.setText(todo.description)
                    if (todo.dueTime > 0) {
                        tvDueDate.text = DateTimeUtils.formatDateTime(todo.dueTime)
                    }
                    swPin.isChecked = todo.isPinned
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this@EditTodoActivity, "待办事项不存在", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    /**
     * 设置点击监听
     */
    private fun setupListeners() {
        // 截止时间选择
        tvDueDate.setOnClickListener {
            showDateTimePicker()
        }

        // 分类选择
        tvCategory.setOnClickListener {
            // 这里可以实现分类选择对话框
            Toast.makeText(this, "分类选择功能待实现", Toast.LENGTH_SHORT).show()
        }

        // 保存按钮
        btnSave.setOnClickListener {
            updateTodo()
        }

        // 删除按钮
        btnDelete.setOnClickListener {
            deleteTodo()
        }
    }

    /**
     * 显示日期时间选择器
     */
    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        if (dueTime > 0) {
            calendar.timeInMillis = dueTime
        }

        // 日期选择
        val datePicker = DatePickerDialog(
            this,
            {
                _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                
                // 时间选择
                val timePicker = TimePickerDialog(
                    this,
                    {
                        _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)
                        
                        dueTime = calendar.timeInMillis
                        tvDueDate.text = DateTimeUtils.formatDateTime(dueTime)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                timePicker.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    /**
     * 更新待办事项
     */
    private fun updateTodo() {
        val title = etTitle.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val isPinned = swPin.isChecked

        // 验证输入
        if (title.isEmpty()) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show()
            return
        }

        // 更新待办事项
        todo.apply {
            this.title = title
            this.description = description
            this.dueTime = dueTime
            this.isPinned = isPinned
            this.categoryId = categoryId
            this.updatedAt = System.currentTimeMillis()
        }

        // 保存到数据库
        CoroutineScope(Dispatchers.IO).launch {
            val result = todoRepository.updateTodo(todo)
            
            runOnUiThread {
                if (result) {
                    Toast.makeText(this@EditTodoActivity, "更新成功", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditTodoActivity, "更新失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * 删除待办事项
     */
    private fun deleteTodo() {
        CoroutineScope(Dispatchers.IO).launch {
            todoRepository.deleteTodo(todo)
            
            runOnUiThread {
                Toast.makeText(this@EditTodoActivity, "删除成功", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

