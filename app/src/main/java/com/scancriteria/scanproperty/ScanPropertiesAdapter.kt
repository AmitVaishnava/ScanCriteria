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

    /*
    *Constant variable
     */
    private val DEFAULT_VALUE = 0
    private val PARENT_VIEW = 0
    private val CHILD_VIEW = 1

    //Dynamic variable
    var enableChildView: Boolean = false
        set(value) {
            field = value
        }

    private var scanPropertieslistener = scanPropertiesAdapterListener
    //Dynamic variable
    var scanProperties = listOf<ScanProperty>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == PARENT_VIEW) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_scan_property, parent, false)
            return ParentViewHolder(view)
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
        if (viewHolder is ParentViewHolder) {
            val item = scanProperties[position]
            viewHolder.itemView.setOnClickListener {
                scanPropertieslistener.onItemClick(position)
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

    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.name_txt)
        private val tagTaxtView: TextView = itemView.findViewById(R.id.tag_txt)
        fun bind(item: ScanProperty) {
            nameTextView.text = item.name
            tagTaxtView.text = item.tag
            if (Constants.ScanVariableColor.GREEN.equals(item.color))
                tagTaxtView.setTextColor(Color.GREEN)
            else
                tagTaxtView.setTextColor(Color.RED)

            if (enableChildView) {
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, android.R.color.holo_blue_bright))
            }
        }
    }

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.name_txt)

        fun bind(item: ScanProperty.Criteria) {
            nameTextView.movementMethod = LinkMovementMethod.getInstance()
            val context = itemView.context
            var criteriaTextvalue: String = item.text.toString()
            /*
            *HashMap<String,Pair<Int,Int>>:
            * String: for key
            * Pair : Location of Left key and Right key
            */
            val positionVariable = HashMap<String, Pair<Int, Int>>()

            item.variable?.let {
                for ((key, value) in item.variable) {

                    var defaultValue = ""
                    if (value.type.equals(Constants.ScanVariableType.INDICATOR_TYPE)) {
                        defaultValue =
                            context.getString(R.string.brackets, UiUtils.getFormattedValue(value.default_value))
                    }

                    if (value.type.equals(Constants.ScanVariableType.VALUE_TYPE)) {
                        defaultValue =
                            context.getString(
                                R.string.brackets,
                                UiUtils.getFormattedValue(value.values?.get(DEFAULT_VALUE))
                            )
                    }

                    criteriaTextvalue = criteriaTextvalue.replace(key, defaultValue)

                    if (criteriaTextvalue.contains(defaultValue)) {
                        positionVariable?.put(
                            key, Pair(
                                criteriaTextvalue.indexOf(defaultValue),
                                criteriaTextvalue.indexOf(defaultValue) + defaultValue.length
                            )
                        )
                    }
                }
            }

            if (positionVariable.size <= 0)
                nameTextView.text = criteriaTextvalue
            else {
                var spannable = UiUtils.spannableBlueText(
                    criteriaTextvalue, positionVariable, object : SpannableListener {
                        override fun onSpanClick(key: String) {
                            var variable = item.variable?.get(key)
                            if (variable != null) {
                                scanPropertieslistener.onSubItemClick(variable)
                            }
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

    interface ScanPropertiesAdapterListener {
        fun onItemClick(position: Int)
        fun onSubItemClick(values: ScanProperty.VariableObj?)
    }
}
