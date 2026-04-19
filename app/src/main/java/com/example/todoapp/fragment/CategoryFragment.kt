package com.example.todoapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.todoapp.R
import com.example.todoapp.base.BaseFragment

/**
 * **`CategoryFragment.kt`** - 分类管理Fragment
 *    - 显示分类列表
 *    - 管理分类操作
 */
class CategoryFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        
        // 添加临时文本
        val textView = view.findViewById<TextView>(R.id.text_view)
        textView.text = "分类管理"
        
        return view
    }
}

