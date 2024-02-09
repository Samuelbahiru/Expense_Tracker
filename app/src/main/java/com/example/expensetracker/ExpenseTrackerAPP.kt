package com.example.expensetracker

import android.app.Application

class ExpenseTrackerAPP: Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}