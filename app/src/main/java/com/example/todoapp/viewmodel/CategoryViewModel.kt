package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Category
import com.example.todoapp.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * **`CategoryViewModel.kt`** - 分类ViewModel
 *    - 管理分类的UI状态
 *    - 处理分类数据加载
 */
class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    
    /**
     * 分类UI状态
     */
    data class CategoryUiState(
        val categories: List<Category> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val isEmpty: Boolean = false
    )
    
    // 状态流
    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState
    
    /**
     * 加载所有分类
     */
    fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val categories = categoryRepository.getAllCategories()
                _uiState.update {
                    it.copy(
                        categories = categories,
                        isLoading = false,
                        isEmpty = categories.isEmpty()
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "加载失败"
                    )
                }
            }
        }
    }
    
    /**
     * 添加分类
     */
    fun addCategory(category: Category): Long {
        var result: Long = -1
        viewModelScope.launch {
            result = categoryRepository.addCategory(category)
            if (result > 0) {
                loadCategories() // 重新加载数据
            }
        }
        return result
    }
    
    /**
     * 删除分类
     */
    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
            loadCategories() // 重新加载数据
        }
    }
}
