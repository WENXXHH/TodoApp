package com.example.todoapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.todoapp.R
import com.example.todoapp.base.BaseFragment

/**
 * **`ProfileFragment.kt`** - 个人中心Fragment
 *    - 显示用户信息
 *    - 提供功能选项
 */
class ProfileFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        
        // 添加临时文本
        val textView = view.findViewById<TextView>(R.id.text_view)
        textView.text = "个人中心"
        
        return view
    }
}

