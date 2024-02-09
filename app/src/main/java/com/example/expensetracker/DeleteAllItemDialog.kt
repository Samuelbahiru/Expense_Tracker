package com.example.expensetracker

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment

class DeleteAllItemDialog: DialogFragment() {
    private lateinit var deleteAllItemsListener: DeleteAllItemsListener
    private lateinit var deleteButton: AppCompatButton
    private lateinit var cancelButton: AppCompatButton

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        var view = LayoutInflater.from(activity).inflate(R.layout.dialog_delete_all_items,null)

        deleteButton = view.findViewById(R.id.delete_button)
        cancelButton = view.findViewById(R.id.cancel_button)

        deleteButton.setOnClickListener { v ->
            run {
                deleteAllItemsListener.onDeleteAllItems()
                dismiss()
            }
        }

        cancelButton.setOnClickListener { v -> dismiss() }

        return AlertDialog.Builder(activity)
            .setView(view)
            .setCancelable(false)
            .create()
    }

    fun setDeleteAllItemsListener(deleteAllItemsListener: DeleteAllItemsListener){
        this.deleteAllItemsListener = deleteAllItemsListener
    }
}