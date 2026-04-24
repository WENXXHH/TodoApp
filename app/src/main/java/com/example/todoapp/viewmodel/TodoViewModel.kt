package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * **`TodoViewModel.kt`** - 待办事项ViewModel
 *    - 管理待办事项的UI状态
 *    - 处理数据加载和操作
 *    - 提供统一的状态管理
 */
class TodoViewModel(private val todoRepository: TodoRepository) : ViewModel() {
    
    /**
     * 待办事项UI状态
     */
    data class TodoUiState(
        val todos: List<TodoItem> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val isEmpty: Boolean = false
    )
    
    // 状态流
    private val _uiState = MutableStateFlow(TodoUiState())
    val uiState: StateFlow<TodoUiState> = _uiState
    
    /**
     * 加载所有待办事项
     */
    fun loadTodos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val todos = todoRepository.getAllTodos()
                _uiState.update {
                    it.copy(
                        todos = todos,
                        isLoading = false,
                        isEmpty = todos.isEmpty()
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
     * 加载未完成待办事项
     */
    fun loadIncompleteTodos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val todos = todoRepository.getIncompleteTodos()
                _uiState.update {
                    it.copy(
                        todos = todos,
                        isLoading = false,
                        isEmpty = todos.isEmpty()
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
     * 切换待办事项完成状态
     */
    fun toggleCompletion(todo: TodoItem) {
        viewModelScope.launch {
            try {
                todoRepository.toggleTodoCompletion(todo)
                loadTodos() // 重新加载数据
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "操作失败"
                    )
                }
            }
        }
    }
    
    /**
     * 删除待办事项
     */
    fun deleteTodo(todo: TodoItem) {
        viewModelScope.launch {
            try {
                todoRepository.deleteTodo(todo)
                loadTodos() // 重新加载数据
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "删除失败"
                    )
                }
            }
        }
    }
    
    /**
     * 搜索待办事项
     */
    fun searchTodos(keyword: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val todos = if (keyword.isBlank()) {
                    todoRepository.getAllTodos()
                } else {
                    todoRepository.searchTodos(keyword)
                }
                _uiState.update {
                    it.copy(
                        todos = todos,
                        isLoading = false,
                        isEmpty = todos.isEmpty()
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "搜索失败"
                    )
                }
            }
        }
    }
}
