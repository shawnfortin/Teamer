package com.fortin.shawn.teamer

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText

class AddNameDialogFragment : DialogFragment() {
    internal lateinit var target: AddNamesListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_names, null)
        return AlertDialog.Builder(activity).setMessage(R.string.add_name)
                .setView(view)
                .setPositiveButton(R.string.add, DialogInterface.OnClickListener({dialog, id ->
                    val edit = view.findViewById<EditText>(R.id.addName)
                    target.onPositiveClicked(this, edit.text.toString())}))
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener({dialog, id -> /*do thing*/}))
                .create()
    }

    fun setTarget(target: AddNamesListener) {
        this.target = target
    }

    interface AddNamesListener {
        fun onPositiveClicked(dialog: AddNameDialogFragment, name: String)
    }
}