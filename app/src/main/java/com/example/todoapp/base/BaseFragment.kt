package com.example.todoapp.base

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * **`BaseFragment.kt`** - 基础Fragment封装
 *    - 提供通用功能和方法
 *    - 统一错误处理
 *    - 简化Toast显示
 */
open class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 显示短时间Toast
     * @param message 消息内容
     */
    protected fun showShortToast(message: String) {
        activity?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 显示长时间Toast
     * @param message 消息内容
     */
    protected fun showLongToast(message: String) {
        activity?.let {
            Toast.makeText(it, message, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * 处理异常
     * @param e 异常
     * @param message 自定义错误消息
     */
    protected fun handleException(e: Exception, message: String = "操作失败") {
        showShortToast("$message: ${e.message}")
        e.printStackTrace()
    }

    /**
     * 检查输入是否为空
     * @param input 输入内容
     * @param message 提示消息
     * @return 是否为空
     */
    protected fun isEmpty(input: String, message: String): Boolean {
        if (input.isBlank()) {
            showShortToast(message)
            return true
        }
        return false
    }
}

