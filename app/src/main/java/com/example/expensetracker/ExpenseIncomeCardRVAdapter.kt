package com.example.expensetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.utils.Constants

class ExpenseIncomeCardRVAdapter(
    private val itemList: List<ExpenseIncomeModel>,private val context: Context, private val cardDeleteIconListener: CardDeleteIconListener):
    RecyclerView.Adapter<ExpenseIncomeCardRVAdapter.ExpenseIncomeCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseIncomeCardViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.layout_expense_income_card,parent,false)
        return ExpenseIncomeCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size;
    }

    override fun onBindViewHolder(holder: ExpenseIncomeCardViewHolder, position: Int) {
        holder.titleTV.text = itemList[position].title
        holder.amountTV.text = itemList[position].amount.toString()
        holder.typeTV.text = itemList[position].type

        if(itemList[position].type == Constants.Types().EXPENSE){
            holder.typeTV.setTextColor(context.getColor(R.color.red))
        }

        holder.deleteButton.setOnClickListener { v -> cardDeleteIconListener.onDeleteListener(itemList[position].id) }


    }

    class ExpenseIncomeCardViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val titleTV : TextView = itemView.findViewById(R.id.titleTV)
        val amountTV : TextView = itemView.findViewById(R.id.amountTV)
        val typeTV : TextView = itemView.findViewById(R.id.typeTV)
        val deleteButton: ImageView = itemView.findViewById(R.id.button_delete)
    }
}