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
 * **`AddTodoViewModel.kt`** - 添加待办事项ViewModel
 *    - 管理添加待办事项的UI状态
 *    - 处理保存逻辑
 */
class AddTodoViewModel(private val todoRepository: TodoRepository) : ViewModel() {
    
    /**
     * 添加待办事项UI状态
     */
    data class AddTodoUiState(
        val isSaving: Boolean = false,
        val saveSuccess: Boolean = false,
        val error: String? = null
    )
    
    // 状态流
    private val _uiState = MutableStateFlow(AddTodoUiState())
    val uiState: StateFlow<AddTodoUiState> = _uiState
    
    /**
     * 保存待办事项
     */
    fun saveTodo(todo: TodoItem) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, error = null) }
            try {
                val result = todoRepository.addTodo(todo)
                if (result > 0) {
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            saveSuccess = true
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            error = "保存失败"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        error = e.message ?: "保存失败"
                    )
                }
            }
        }
    }
    
    /**
     * 重置UI状态
     */
    fun resetState() {
        _uiState.value = AddTodoUiState()
    }
}
