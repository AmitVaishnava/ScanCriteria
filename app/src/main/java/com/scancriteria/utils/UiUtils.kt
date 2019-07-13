package com.scancriteria.utils

import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.scancriteria.ScanApp
import com.scancriteria.scanproperty.ScanPropertiesAdapter


object UiUtils {

    fun spannableBlueText(
        text: String,
        startPosition: HashMap<String, Pair<Int, Int>>,
        param: ScanPropertiesAdapter.SpannableListener
    ): SpannableString {
        val color = ContextCompat.getColor(ScanApp.instance, android.R.color.holo_blue_light)
        val spannableString = SpannableString(text)

        if (startPosition.size > 0) {
            for (mutableEntry in startPosition) {
                spannableString.setSpan(object : ClickableSpan() {
                    override fun onClick(view: View) {
                        param.onSpanClick(mutableEntry.key)
                    }
                }, mutableEntry.value.first, mutableEntry.value.second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                spannableString.setSpan(
                    ForegroundColorSpan(color), mutableEntry.value.first,
                    mutableEntry.value.second, 0
                )
            }
        }
        return spannableString
    }
}