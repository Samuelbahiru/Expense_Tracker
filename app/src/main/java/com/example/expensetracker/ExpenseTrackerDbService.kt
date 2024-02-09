package com.example.expensetracker

import android.content.Context
import com.example.expensetracker.utils.Constants
import io.objectbox.Box
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()
    }
}

class ExpenseTrackerDbService(context: Context) {
    private val expenseIncomeStore: Box<ExpenseIncomeModel> = ObjectBox.boxStore.boxFor(ExpenseIncomeModel::class.java)

    companion object {
        @Volatile
        private var instance: ExpenseTrackerDbService? = null

        fun getInstance(context: Context): ExpenseTrackerDbService {
            return instance ?: synchronized(this) {
                instance ?: ExpenseTrackerDbService(context).also { instance = it }
            }
        }
    }

    fun getAllItems(): List<ExpenseIncomeModel> {
        val items: List<ExpenseIncomeModel> = expenseIncomeStore.all
        println("total items ${items.size}")
        return items
    }

    fun deleteItem(id: Long) {
        expenseIncomeStore.remove(id)
    }
    fun deleteAllItems() {
        println("TO BE DELETED")
        expenseIncomeStore.removeAll()
        println("SUCCESSFULLY DELETED")
    }
    fun addItem(item: ExpenseIncomeModel){
        println("GOING TO ADD ITEM")
        expenseIncomeStore.put(item)
    }

    fun getExpenseSummary(): Int {
        var expenseSummary = 0;
        val items: List<ExpenseIncomeModel> = expenseIncomeStore.all.filter {
            item -> item.type == Constants.Types().EXPENSE
        }
        for (item in items){
            if(item.amount != null){
                expenseSummary += item.amount!!
            }
        }
        return expenseSummary
    }

    fun getIncomeSummary(): Int {
        var incomeSummary = 0;
        val items: List<ExpenseIncomeModel> = expenseIncomeStore.all.filter {
                item -> item.type == Constants.Types().INCOME
        }
        for (item in items){
            if(item.amount != null){
                incomeSummary += item.amount!!
            }
        }
        return incomeSummary
    }
}