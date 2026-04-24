package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.repository.TodoRepository

/**
 * **`AddTodoViewModelFactory.kt`** - 添加待办事项ViewModel工厂
 *    - 创建AddTodoViewModel实例
 *    - 提供依赖注入
 */
class AddTodoViewModelFactory(
    private val todoRepository: TodoRepository = TodoRepository()
) : ViewModelProvider.Factory {
    
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddTodoViewModel(todoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
