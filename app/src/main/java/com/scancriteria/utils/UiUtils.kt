package com.scancriteria.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.scancriteria.ScanApp
import com.scancriteria.scanproperty.ScanPropertiesAdapter


object UiUtils {

    fun spannableBlueBrackets(
        text: String,
        startPosition: HashMap<String, Pair<Int, Int>>,
        param: ScanPropertiesAdapter.SpannableListener
//        ,        clickableSpan: Array<ClickableSpan>
    ): SpannableString {
        val context = ScanApp.instance
        val color = ContextCompat.getColor(context, android.R.color.holo_blue_light)
        val spannableString = SpannableString(text)

        if (startPosition.size > 0) {
            for (mutableEntry in startPosition) {
                spannableString.setSpan(
                    ForegroundColorSpan(color),
                    mutableEntry.value.first,
                    mutableEntry.value.second,
                    0
                )
                spannableString.setSpan(object : ClickableSpan() {
                    override fun onClick(view: View) {
                        param.onSpanClick(mutableEntry.key)
                    }
                }, mutableEntry.value.first, mutableEntry.value.second, 0)
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

