package com.example.expensetracker

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class ExpenseIncomeModel {
    @Id
    var id: Long = 0
    var title: String? = null
    var amount: Int? = null
    var type: String? = null
}