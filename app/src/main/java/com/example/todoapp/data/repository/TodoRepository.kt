package com.example.todoapp.data.repository

import com.example.todoapp.data.database.AppDatabase
import com.example.todoapp.data.database.TodoDao
import com.example.todoapp.data.model.TodoItem

/**
 * **`TodoRepository.kt`** - 待办事项业务逻辑封装
 *    - 封装数据操作
 *    - 处理事项状态更新
 *    - 管理提醒设置
 *    - 实现搜索过滤功能
 */
class TodoRepository(private val todoDao: TodoDao) {

    /**
     * 构造函数，使用默认的TodoDao实例
     */
    constructor() : this(AppDatabase.database.todoDao())

    /**
     * 添加待办事项
     * @param todoItem 待办事项
     * @return 添加结果，成功返回ID，失败返回-1
     */
    suspend fun addTodo(todoItem: TodoItem): Long {
        if (!todoItem.isValid()) {
            return -1
        }
        return todoDao.insert(todoItem)
    }

    /**
     * 更新待办事项
     * @param todoItem 待办事项
     * @return 更新是否成功
     */
    suspend fun updateTodo(todoItem: TodoItem): Boolean {
        if (!todoItem.isValid()) {
            return false
        }
        todoDao.update(todoItem)
        return true
    }

    /**
     * 删除待办事项
     * @param todoItem 待办事项
     */
    suspend fun deleteTodo(todoItem: TodoItem) {
        todoDao.delete(todoItem)
    }

    /**
     * 根据ID获取待办事项
     * @param id 事项ID
     * @return 待办事项
     */
    suspend fun getTodoById(id: Long): TodoItem? {
        return todoDao.getById(id)
    }

    /**
     * 获取所有待办事项
     * @return 待办事项列表
     */
    suspend fun getAllTodos(): List<TodoItem> {
        return todoDao.getAll()
    }

    /**
     * 获取未完成的待办事项
     * @return 待办事项列表
     */
    suspend fun getIncompleteTodos(): List<TodoItem> {
        return todoDao.getIncomplete()
    }

    /**
     * 获取已完成的待办事项
     * @return 待办事项列表
     */
    suspend fun getCompletedTodos(): List<TodoItem> {
        return todoDao.getCompleted()
    }

    /**
     * 根据分类获取待办事项
     * @param categoryId 分类ID
     * @return 待办事项列表
     */
    suspend fun getTodosByCategory(categoryId: Long): List<TodoItem> {
        return todoDao.getByCategory(categoryId)
    }

    /**
     * 切换待办事项完成状态
     * @param todoItem 待办事项
     * @return 更新是否成功
     */
    suspend fun toggleTodoCompletion(todoItem: TodoItem): Boolean {
        todoItem.toggleCompleted()
        return updateTodo(todoItem)
    }

    /**
     * 切换待办事项置顶状态
     * @param todoItem 待办事项
     * @return 更新是否成功
     */
    suspend fun toggleTodoPinned(todoItem: TodoItem): Boolean {
        todoItem.togglePinned()
        return updateTodo(todoItem)
    }

    /**
     * 搜索待办事项
     * @param keyword 搜索关键词
     * @return 待办事项列表
     */
    suspend fun searchTodos(keyword: String): List<TodoItem> {
        return todoDao.search("%$keyword%")
    }

    /**
     * 获取需要提醒的待办事项
     * @return 待办事项列表
     */
    suspend fun getReminders(): List<TodoItem> {
        return todoDao.getReminders(System.currentTimeMillis())
    }

    /**
     * 获取过期的待办事项
     * @return 待办事项列表
     */
    suspend fun getOverdueTodos(): List<TodoItem> {
        return todoDao.getOverdue(System.currentTimeMillis())
    }
}

