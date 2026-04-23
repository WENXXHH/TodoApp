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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.R
import com.example.todoapp.base.BaseActivity
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.utils.DateTimeUtils
import com.example.todoapp.viewmodel.AddTodoViewModel
import com.example.todoapp.viewmodel.AddTodoViewModelFactory
import kotlinx.coroutines.flow.collect
import java.util.Calendar

/**
 * **`AddTodoActivity.kt`** - 添加待办事项逻辑
 *    - 初始化表单字段
 *    - 处理日期时间选择
 *    - 验证输入数据
 *    - 保存事项到数据库
 */
class AddTodoActivity : BaseActivity() {

    // UI组件
    private lateinit var toolbar: Toolbar
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var tvDueDate: TextView
    private lateinit var tvReminder: TextView
    private lateinit var tvCategory: TextView
    private lateinit var swPin: Switch
    private lateinit var btnSave: Button

    // 数据
    private var dueTime: Long = 0
    private var reminderTime: Long = 0
    private var categoryId: Long = 0
    private lateinit var addTodoViewModel: AddTodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_form)

        // 初始化UI组件
        initViews()

        // 初始化ViewModel
        initViewModel()

        // 设置工具栏
        setupToolbar()

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
        tvReminder = findViewById(R.id.tv_reminder)
        tvCategory = findViewById(R.id.tv_category)
        swPin = findViewById(R.id.sw_pin)
        btnSave = findViewById(R.id.btn_save)
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        addTodoViewModel = ViewModelProvider(this, AddTodoViewModelFactory())[AddTodoViewModel::class.java]
        
        // 观察UI状态变化
        lifecycleScope.launchWhenStarted {
            addTodoViewModel.uiState.collect {
                when {
                    it.isSaving -> {
                        // 显示保存中状态
                        showSaving()
                    }
                    it.saveSuccess -> {
                        // 保存成功
                        showSaveSuccess()
                    }
                    it.error != null -> {
                        // 显示错误信息
                        showError(it.error)
                    }
                }
            }
        }
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
        // 截止时间选择
        tvDueDate.setOnClickListener {
            showDateTimePicker()
        }

        // 提醒时间选择
        tvReminder.setOnClickListener {
            showReminderDateTimePicker()
        }

        // 分类选择
        tvCategory.setOnClickListener {
            // 这里可以实现分类选择对话框
            Toast.makeText(this, "分类选择功能待实现", Toast.LENGTH_SHORT).show()
        }

        // 保存按钮
        btnSave.setOnClickListener {
            saveTodo()
        }
    }

    /**
     * 显示日期时间选择器
     */
    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()

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
     * 显示提醒日期时间选择器
     */
    private fun showReminderDateTimePicker() {
        val calendar = Calendar.getInstance()

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
                        
                        reminderTime = calendar.timeInMillis
                        tvReminder.text = DateTimeUtils.formatDateTime(reminderTime)
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
     * 保存待办事项
     */
    private fun saveTodo() {
        val title = etTitle.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val isPinned = swPin.isChecked

        // 验证输入
        if (title.isEmpty()) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show()
            return
        }

        // 创建待办事项
        val todo = TodoItem().apply {
            this.title = title
            this.description = description
            this.dueTime = dueTime
            this.reminderTime = reminderTime
            this.isPinned = isPinned
            this.categoryId = categoryId
        }

        // 保存到数据库
        addTodoViewModel.saveTodo(todo)
    }

    /**
     * 显示保存中状态
     */
    private fun showSaving() {
        btnSave.isEnabled = false
        btnSave.text = "保存中..."
    }

    /**
     * 显示保存成功
     */
    private fun showSaveSuccess() {
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show()
        finish()
    }

    /**
     * 显示错误信息
     */
    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        btnSave.isEnabled = true
        btnSave.text = "保存"
    }
}

