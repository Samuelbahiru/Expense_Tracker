package com.example.expensetracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SummaryActivity : AppCompatActivity() {
    fun getInstance(context: Context): Intent {
        return Intent(context,SummaryActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val totalExpense = ExpenseTrackerDbService(this).getExpenseSummary()
        val totalIncome = ExpenseTrackerDbService(this).getIncomeSummary()
        val balance = totalIncome - totalExpense

        val totalExpenseTV = findViewById<TextView>(R.id.total_expense_tv)
        totalExpenseTV.text = totalExpense.toString()

        val totalIncomeTV = findViewById<TextView>(R.id.total_income_tv)
        totalIncomeTV.text = totalIncome.toString()

        val balanceTV = findViewById<TextView>(R.id.balance_tv)
        balanceTV.text = balance.toString()

        if(balance >= 0)balanceTV.setTextColor(this.getColor(R.color.green))
    }
}