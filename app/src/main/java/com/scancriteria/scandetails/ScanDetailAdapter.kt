package com.scancriteria.scandetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.scancriteria.R

class ScanDetailAdapter : RecyclerView.Adapter<ScanDetailAdapter.ViewHolder>() {

    var scanDetails = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = scanDetails[position]
        viewHolder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_item_view, parent, false) as TextView
        return ViewHolder(view)

    }

    override fun getItemCount() = scanDetails.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val valueText: TextView = view.findViewById(R.id.value_text)
        fun bind(item: String) {
            valueText.text = item
        }

    }
}
