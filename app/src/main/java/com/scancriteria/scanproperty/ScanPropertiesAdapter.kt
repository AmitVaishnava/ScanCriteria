package com.scancriteria.scanproperty

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.scancriteria.R

class ScanPropertiesAdapter(scanPropertiesAdapterListener: ScanPropertiesAdapterListener) :
    RecyclerView.Adapter<ScanPropertiesAdapter.ViewHolder>() {
    var listener = scanPropertiesAdapterListener
    var scanProperties = listOf<ScanProperty>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_scan_property, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = scanProperties.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = scanProperties[position]
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_txt)
        fun bind(item: ScanProperty) {
            val res = itemView.context.resources
            name.text = item.name
        }
    }

    interface ScanPropertiesAdapterListener {
        fun onItemClick(position: Int)
    }
}
