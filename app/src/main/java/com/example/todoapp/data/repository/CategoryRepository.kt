package com.example.todoapp.data.repository

import com.example.todoapp.data.database.AppDatabase
import com.example.todoapp.data.database.CategoryDao
import com.example.todoapp.data.model.Category

/**
 * **`CategoryRepository.kt`** - 分类业务逻辑封装
 *    - 封装分类数据操作
 *    - 处理分类名称验证
 *    - 管理分类的增删改查
 */
class CategoryRepository(private val categoryDao: CategoryDao) {

    /**
     * 构造函数，使用默认的CategoryDao实例
     */
    constructor() : this(AppDatabase.database.categoryDao())

    /**
     * 添加分类
     * @param category 分类
     * @return 添加结果，成功返回ID，失败返回-1
     */
    suspend fun addCategory(category: Category): Long {
        if (!category.isNameValid()) {
            return -1
        }

        if (categoryDao.isNameExists(category.name) > 0) {
            return -2
        }

        return categoryDao.insert(category)
    }

    /**
     * 更新分类
     * @param category 分类
     * @return 更新是否成功
     */
    suspend fun updateCategory(category: Category): Boolean {
        if (!category.isNameValid()) {
            return false
        }

        val existingCategory = categoryDao.getByName(category.name)
        if (existingCategory != null && existingCategory.id != category.id) {
            return false
        }

        category.updatedAt = System.currentTimeMillis()
        categoryDao.update(category)
        return true
    }

    /**
     * 删除分类
     * @param category 分类
     */
    suspend fun deleteCategory(category: Category) {
        categoryDao.delete(category)
    }

    /**
     * 根据ID获取分类
     * @param id 分类ID
     * @return 分类
     */
    suspend fun getCategoryById(id: Long): Category? {
        return categoryDao.getById(id)
    }

    /**
     * 获取所有分类
     * @return 分类列表
     */
    suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAll()
    }

    /**
     * 检查分类名称是否存在
     * @param name 分类名称
     * @return 是否存在
     */
    suspend fun isCategoryNameExists(name: String): Boolean {
        return categoryDao.isNameExists(name) > 0
    }

    /**
     * 获取分类下的待办事项数量
     * @param categoryId 分类ID
     * @return 待办事项数量
     */
    suspend fun getCategoryTodoCount(categoryId: Long): Int {
        return categoryDao.getTodoCount(categoryId)
    }
}