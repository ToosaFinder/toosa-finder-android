package com.toosafinder.eventCreation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.toosafinder.R

class CloseDialog(val parent: AppCompatActivity) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(R.string.prompt_close_event_creation)
            .setNegativeButton(R.string.button_cancel) { _, _ -> parentFragmentManager.beginTransaction().remove(this).commit() }
            .setPositiveButton(R.string.button_continue) { _, _ ->
                parentFragmentManager.beginTransaction().remove(this).commit()
                parent.finish()
            }
        return builder.create()
    }
}