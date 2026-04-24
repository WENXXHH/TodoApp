package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.repository.TodoRepository

/**
 * **`TodoViewModelFactory.kt`** - 待办事项ViewModel工厂
 *    - 创建TodoViewModel实例
 *    - 提供依赖注入
 */
class TodoViewModelFactory(
    private val todoRepository: TodoRepository = TodoRepository()
) : ViewModelProvider.Factory {
    
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(todoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
