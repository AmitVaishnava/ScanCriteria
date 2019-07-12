package com.scancriteria.scanproperty

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.scancriteria.R
import com.scancriteria.utils.Constants
import com.scancriteria.utils.UiUtils

class ScanPropertiesAdapter(scanPropertiesAdapterListener: ScanPropertiesAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val PARENT_VIEW = 0
    val CHILD_VIEW = 1

    var enableChildView: Boolean = false
        set(value) {
            field = value
        }

    var listener = scanPropertiesAdapterListener
    var scanProperties = listOf<ScanProperty>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        if (p1 == PARENT_VIEW) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_scan_property, parent, false)
            return ViewHolder(view)
        } else {
            val childView =
                LayoutInflater.from(parent.context).inflate(R.layout.adapter_scan_child_property, parent, false)
            return ChildViewHolder(childView)
        }
    }

    override fun getItemCount(): Int {
        if (!enableChildView) {
            return scanProperties.size
        } else {
            return scanProperties.size + scanProperties[PARENT_VIEW].criteria.size
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ViewHolder) {
            val item = scanProperties[position]
            viewHolder.itemView.setOnClickListener {
                listener.onItemClick(position)
            }
            viewHolder.bind(item)
        } else {
            (viewHolder as ChildViewHolder).bind(item = scanProperties[PARENT_VIEW].criteria[position - 1])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (enableChildView) {
            if (position == 0)
                return PARENT_VIEW
            else
                return CHILD_VIEW
        }
        return PARENT_VIEW
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name_txt)
        val tagTaxtView: TextView = itemView.findViewById(R.id.tag_txt)
        fun bind(item: ScanProperty) {
            val res = itemView.context.resources
            nameTextView.text = item.name
            tagTaxtView.text = item.tag
            if (Constants.GREEN.equals(item.color))
                tagTaxtView.setTextColor(Color.GREEN)
            else
                tagTaxtView.setTextColor(Color.RED)

            if (enableChildView) {
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, android.R.color.holo_blue_bright))
            }
        }
    }

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //        private lateinit var clickableSpan: Array<ClickableSpan>
        val nameTextView: TextView = itemView.findViewById(R.id.name_txt)

        fun bind(item: ScanProperty.Criteria) {
            nameTextView.movementMethod = LinkMovementMethod.getInstance()
            val context = itemView.context
            var valueText: String = item.text.toString()
//            var startPosition: MutableList<Int>? = null
//            var endPosition: MutableList<Int>? = null
            var positionVariable = HashMap<String, kotlin.Pair<Int, Int>>()
            item.variable?.let {
                for ((key, value) in item.variable) {
                    if (value.type.equals(Constants.TYPE_INDICATOR)) {
                        valueText = value.default_value.toString()

                    } else if (value.type.equals(Constants.TYPE_VALUE)) {
                        var value = context.getString(R.string.brackets, value.values?.get(0).toString())
                        valueText = valueText.replace(key, value)
                        if (valueText.contains(context.getString(R.string.open_brackets))) {
                            positionVariable?.put(
                                key,
                                Pair(valueText.indexOf(value), valueText.indexOf(value) + value.length + 1)
                            )
                        }
                    }
                }
            }

//            if (startPosition != null) {
//                startPosition?.forEachIndexed { index, i ->
//                    clickableSpan[index] = object : ClickableSpan() {
//                        override fun onClick(textView: View) {
//                            Log.d("amit", "asd")
//                        }
//                    }
//                }
//            }
            if (positionVariable.size <= 0)
                nameTextView.text = valueText
            else {
                var spannable = UiUtils.spannableBlueBrackets(
                    valueText, positionVariable, object : SpannableListener {
                        override fun onSpanClick(key: String) {

                        }
                    }
                )
                nameTextView.text = spannable
            }
        }
    }

    interface SpannableListener {
        fun onSpanClick(position: String)
    }

//    var clickableSpan: ClickableSpan = object : ClickableSpan() {
//        override fun onClick(textView: View) {
//            Log.d("amit", "asd")
//        }}
//    fun launchDialog(itemView: View, key: String, values: Array<String>?) {
//        UiUtils.showUpdateDialog(itemView.context, "", values,
//            DialogInterface.OnClickListener { dialog, which ->
//                val hashMap: HashMap<Int, String> = HashMap<Int, String>()
//                hashMap.put(which, key)
//                itemView.setTag(hashMap)
//                notifyDataSetChanged()
//            })
//    }

    interface ScanPropertiesAdapterListener {
        fun onItemClick(position: Int)
    }
}
