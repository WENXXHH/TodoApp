package com.example.todoapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.adapter.CategoryAdapter
import com.example.todoapp.base.BaseFragment
import com.example.todoapp.data.model.Category
import com.example.todoapp.data.repository.CategoryRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * **`CategoryFragment.kt`** - 分类管理Fragment
 *    - 显示分类列表
 *    - 管理分类操作（添加、编辑、删除）
 */
class CategoryFragment : BaseFragment() {

    // UI组件
    private lateinit var rvCategories: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var fabAdd: FloatingActionButton

    // 适配器和数据
    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryRepository = CategoryRepository()
    private var categories = emptyList<Category>()
    private var todoCounts = emptyMap<Long, Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        
        // 初始化UI组件
        initViews(view)
        
        // 初始化适配器
        initAdapter()
        
        // 设置点击监听
        setupListeners()
        
        // 加载分类数据
        loadCategories()
        
        return view
    }

    /**
     * 初始化UI组件
     */
    private fun initViews(view: View) {
        rvCategories = view.findViewById(R.id.rv_categories)
        tvEmpty = view.findViewById(R.id.tv_empty)
        fabAdd = view.findViewById(R.id.fab_add)
    }

    /**
     * 初始化适配器
     */
    private fun initAdapter() {
        categoryAdapter = CategoryAdapter(
            categories = categories,
            todoCounts = todoCounts,
            onCategoryClick = { category ->
                // 点击分类，编辑分类
                showEditCategoryDialog(category)
            },
            onCategoryLongClick = { category ->
                // 长按分类，删除分类
                showDeleteCategoryDialog(category)
                true
            }
        )
        
        rvCategories.layoutManager = LinearLayoutManager(requireContext())
        rvCategories.adapter = categoryAdapter
    }

    /**
     * 设置点击监听
     */
    private fun setupListeners() {
        // 添加分类按钮
        fabAdd.setOnClickListener {
            showAddCategoryDialog()
        }
    }

    /**
     * 加载分类数据
     */
    private fun loadCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            // 获取所有分类
            categories = categoryRepository.getAllCategories()
            
            // 获取每个分类的待办事项数量
            val counts = mutableMapOf<Long, Int>()
            categories.forEach {
                counts[it.id] = categoryRepository.getCategoryTodoCount(it.id)
            }
            todoCounts = counts
            
            // 更新UI
            requireActivity().runOnUiThread {
                updateUI()
            }
        }
    }

    /**
     * 更新UI
     */
    private fun updateUI() {
        if (categories.isEmpty()) {
            // 显示空状态
            tvEmpty.visibility = View.VISIBLE
            rvCategories.visibility = View.GONE
        } else {
            // 显示分类列表
            tvEmpty.visibility = View.GONE
            rvCategories.visibility = View.VISIBLE
            categoryAdapter.updateData(categories, todoCounts)
        }
    }

    /**
     * 显示添加分类对话框
     */
    private fun showAddCategoryDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_add_category, null)

        val tvTitle = dialogView.findViewById<TextView>(R.id.tv_title)
        val etName = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.et_name)
        val tilName = dialogView.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.til_name)
        val btnCancel = dialogView.findViewById<android.widget.Button>(R.id.btn_cancel)
        val btnSave = dialogView.findViewById<android.widget.Button>(R.id.btn_save)

        // 颜色选择
        val colorViews = listOf(
            dialogView.findViewById<View>(R.id.color_1),
            dialogView.findViewById<View>(R.id.color_2),
            dialogView.findViewById<View>(R.id.color_3),
            dialogView.findViewById<View>(R.id.color_4),
            dialogView.findViewById<View>(R.id.color_5)
        )

        val colors = listOf(
            "@color/主色",
            "@color/红色",
            "@color/绿色",
            "@color/蓝色",
            "@color/黄色"
        )

        var selectedColor = "#6200EE" // 默认紫色

        // 设置颜色点击事件
        colorViews.forEachIndexed { index, view ->
            view.setOnClickListener {
                // 清除所有颜色的选中状态
                colorViews.forEach { v ->
                    v.setBackgroundResource(R.drawable.bg_category_chip)
                }
                // 设置当前颜色的选中状态
                view.setBackgroundResource(R.drawable.bg_category_chip_selected)
                // 获取选中的颜色
                selectedColor = when (index) {
                    0 -> "#6200EE" // 紫色
                    1 -> "#F44336" // 红色
                    2 -> "#4CAF50" // 绿色
                    3 -> "#2196F3" // 蓝色
                    4 -> "#FF9800" // 黄色
                    else -> "#6200EE"
                }
            }
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            
            if (name.isEmpty()) {
                tilName.error = "请输入分类名称"
                return@setOnClickListener
            }

            // 创建分类
            val category = Category().apply {
                this.name = name
                this.color = selectedColor
            }

            // 保存分类
            CoroutineScope(Dispatchers.IO).launch {
                val result = categoryRepository.addCategory(category)
                
                requireActivity().runOnUiThread {
                    when (result) {
                        -1L -> Toast.makeText(requireContext(), "分类名称无效", Toast.LENGTH_SHORT).show()
                        -2L -> Toast.makeText(requireContext(), "分类名称已存在", Toast.LENGTH_SHORT).show()
                        else -> {
                            Toast.makeText(requireContext(), "添加成功", Toast.LENGTH_SHORT).show()
                            loadCategories() // 重新加载数据
                            dialog.dismiss()
                        }
                    }
                }
            }
        }

        dialog.show()
    }

    /**
     * 显示编辑分类对话框
     */
    private fun showEditCategoryDialog(category: Category) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_add_category, null)

        val tvTitle = dialogView.findViewById<TextView>(R.id.tv_title)
        val etName = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.et_name)
        val tilName = dialogView.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.til_name)
        val btnCancel = dialogView.findViewById<android.widget.Button>(R.id.btn_cancel)
        val btnSave = dialogView.findViewById<android.widget.Button>(R.id.btn_save)

        // 设置标题和默认值
        tvTitle.text = "编辑分类"
        etName.setText(category.name)

        // 颜色选择
        val colorViews = listOf(
            dialogView.findViewById<View>(R.id.color_1),
            dialogView.findViewById<View>(R.id.color_2),
            dialogView.findViewById<View>(R.id.color_3),
            dialogView.findViewById<View>(R.id.color_4),
            dialogView.findViewById<View>(R.id.color_5)
        )

        var selectedColor = category.color

        // 设置颜色点击事件
        colorViews.forEachIndexed { index, view ->
            // 检查当前颜色是否匹配
            val color = when (index) {
                0 -> "#6200EE" // 紫色
                1 -> "#F44336" // 红色
                2 -> "#4CAF50" // 绿色
                3 -> "#2196F3" // 蓝色
                4 -> "#FF9800" // 黄色
                else -> "#6200EE"
            }

            if (color == selectedColor) {
                view.setBackgroundResource(R.drawable.bg_category_chip_selected)
            } else {
                view.setBackgroundResource(R.drawable.bg_category_chip)
            }

            view.setOnClickListener {
                // 清除所有颜色的选中状态
                colorViews.forEach { v ->
                    v.setBackgroundResource(R.drawable.bg_category_chip)
                }
                // 设置当前颜色的选中状态
                view.setBackgroundResource(R.drawable.bg_category_chip_selected)
                // 获取选中的颜色
                selectedColor = color
            }
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            
            if (name.isEmpty()) {
                tilName.error = "请输入分类名称"
                return@setOnClickListener
            }

            // 更新分类
            category.name = name
            category.color = selectedColor

            // 保存分类
            CoroutineScope(Dispatchers.IO).launch {
                val result = categoryRepository.updateCategory(category)
                
                requireActivity().runOnUiThread {
                    if (result) {
                        Toast.makeText(requireContext(), "更新成功", Toast.LENGTH_SHORT).show()
                        loadCategories() // 重新加载数据
                        dialog.dismiss()
                    } else {
                        Toast.makeText(requireContext(), "更新失败，分类名称可能已存在", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        dialog.show()
    }

    /**
     * 显示删除分类对话框
     */
    private fun showDeleteCategoryDialog(category: Category) {
        AlertDialog.Builder(requireContext())
            .setTitle("删除分类")
            .setMessage("确定要删除这个分类吗？")
            .setPositiveButton("确定") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    categoryRepository.deleteCategory(category)
                    
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "删除成功", Toast.LENGTH_SHORT).show()
                        loadCategories() // 重新加载数据
                    }
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    /**
     * 当Fragment重新可见时，刷新数据
     */
    override fun onResume() {
        super.onResume()
        loadCategories()
    }
}
