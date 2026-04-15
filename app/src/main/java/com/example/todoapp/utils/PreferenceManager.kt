package com.example.todoapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("todo_app", Context.MODE_PRIVATE)
}
