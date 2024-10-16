package com.AngelHernandez.mytodoapp.ui.view.dependencias

import android.app.AlertDialog
import android.content.Context

class Dialogs {

    fun Context.showSimpleDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}