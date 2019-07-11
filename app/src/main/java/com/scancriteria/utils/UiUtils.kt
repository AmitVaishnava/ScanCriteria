package com.scancriteria.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.scancriteria.ScanApp


object UiUtils {

    fun spannableBlueBrackets(
        text: String,
        startPosition: MutableList<Int>?,
        endPosition: MutableList<Int>?
//        ,        clickableSpan: Array<ClickableSpan>
    ): SpannableString {
        val context = ScanApp.getInstance()
        val color = ContextCompat.getColor(context, android.R.color.holo_blue_light)
        val spannableString = SpannableString(text)
        if (startPosition != null && endPosition != null) {
            startPosition.forEachIndexed { index, i ->
                spannableString.setSpan(
                    ForegroundColorSpan(color),
                    startPosition.get(index),
                    endPosition.get(index) + 1,
                    0
                )
//                spannableString.setSpan(clickableSpan,startPosition.get(index), endPosition.get(index)+1, 0)
            }
        }
        return spannableString
    }

    fun showUpdateDialog(
        context: Context,
        title: String,
        arrayOf: Array<String>?,
        dialogInterface: DialogInterface.OnClickListener
    ) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose an criteria")
        builder.setItems(arrayOf, dialogInterface)
        val dialog = builder.create()
        dialog.show()
    }

}

