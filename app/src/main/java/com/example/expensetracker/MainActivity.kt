package com.example.expensetracker

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.utils.Constants

class MainActivity :
    AppCompatActivity(),CardDeleteIconListener,DeleteAllItemsListener {
    private lateinit var titleEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var expenseRadio : RadioButton
    private lateinit var incomeRadio : RadioButton
    private lateinit var saveButton : AppCompatButton
    private var type: String? = null
    private lateinit var itemCardRv: RecyclerView
    private lateinit var noItemTextView: TextView
    private lateinit var deleteAllButton: AppCompatButton
    private lateinit var summaryButton: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        ObjectBox.init(this)

        titleEditText = findViewById(R.id.edit_text_title)
        amountEditText = findViewById(R.id.edit_text_amount)
        expenseRadio = findViewById(R.id.type_expense_radio)
        incomeRadio = findViewById(R.id.type_income_radio)
        saveButton = findViewById(R.id.button_save)
        itemCardRv = findViewById(R.id.expense_income_card_rv)
        noItemTextView = findViewById(R.id.no_expenses_tv)
        deleteAllButton = findViewById(R.id.button_delete_all)
        summaryButton = findViewById(R.id.button_summary)

//        expenseIncomeViewModel = ViewModelProvider(this,factory)[ExpenseIncomeViewModel::class.java]

        expenseRadio.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                if (isChecked) {
                    type = Constants.Types().EXPENSE
                }
            }
        }

        incomeRadio.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                if (isChecked) {
                    type = Constants.Types().INCOME
                }
            }
        }

        saveButton.setOnClickListener { view ->
            run {
                if (validateRequiredFeild(titleEditText, "Please enter Title. it is required!!") &&
                    validateRequiredFeild(amountEditText, "Please enter Amount. it is required!!")
                ) {
                    if(type != null){
                        val expenseIncomeModel = ExpenseIncomeModel()
                        expenseIncomeModel.title = titleEditText.text.toString()
                        expenseIncomeModel.amount = amountEditText.text.toString().toInt()
                        expenseIncomeModel.type = type
                        ExpenseTrackerDbService(this).addItem(expenseIncomeModel)
                        populateRecyclerView()
                        Toast.makeText(this,"Saved!!",Toast.LENGTH_SHORT).show()

                        var radioGroup = findViewById<RadioGroup>(R.id.type_radio_group)
                        radioGroup.clearCheck()

                        titleEditText.text.clear()
                        amountEditText.text.clear()
                        type = null
                    }else{
                        Toast.makeText(this,"Please select type!!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        deleteAllButton.setOnClickListener { v ->
            run {
                val deleteAllItemDialog = DeleteAllItemDialog()
                deleteAllItemDialog.setDeleteAllItemsListener(this)
                deleteAllItemDialog.show(supportFragmentManager,"Delete all items dialog")
            }
        }

        summaryButton.setOnClickListener { v -> startActivity(SummaryActivity().getInstance(this)) }

        populateRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        populateRecyclerView()
    }

    fun validateRequiredFeild(inputField: EditText,message: String): Boolean {
        var isValid = true;
        val input = inputField.text.toString()

        if(input.isEmpty()){
            isValid = false
            inputField.error = message
            inputField.requestFocus()
        }

        return isValid
    }

    fun populateRecyclerView(){
        val items = ExpenseTrackerDbService(this).getAllItems()

        if(items.isNotEmpty()){
            val expenseIncomeCardRVAdapter = ExpenseIncomeCardRVAdapter(items, this, this)
            itemCardRv.adapter = expenseIncomeCardRVAdapter
            itemCardRv.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            itemCardRv.visibility = View.VISIBLE
            noItemTextView.visibility = View.GONE
        }else{
            itemCardRv.visibility = View.GONE
            noItemTextView.visibility = View.VISIBLE
        }
    }

    override fun onDeleteListener(id: Long?) {
        if (id != null) {
            ExpenseTrackerDbService(this).deleteItem(id)
            populateRecyclerView()
            Toast.makeText(this,"Item deleted!!",Toast.LENGTH_SHORT).show()
        };
    }

    override fun onDeleteAllItems() {
        ExpenseTrackerDbService(this).deleteAllItems()
        populateRecyclerView()
        Toast.makeText(this,"All Items deleted!!",Toast.LENGTH_SHORT).show()
    }


}