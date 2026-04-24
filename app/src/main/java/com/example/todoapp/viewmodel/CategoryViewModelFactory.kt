package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.repository.CategoryRepository

/**
 * **`CategoryViewModelFactory.kt`** - 分类ViewModel工厂
 *    - 创建CategoryViewModel实例
 *    - 提供依赖注入
 */
class CategoryViewModelFactory(
    private val categoryRepository: CategoryRepository = CategoryRepository()
) : ViewModelProvider.Factory {
    
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
