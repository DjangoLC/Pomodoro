package com.example.pomodoro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class HistoryAdapter : BaseExpandableListAdapter() {

    var headers = listOf<String>()
    var items = mapOf<String, List<String>>()

    override fun getGroupCount(): Int {
        return headers.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return items[headers[groupPosition]]?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): Any {
        return headers[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return items[headers[groupPosition]]?.get(childPosition)!!
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val inflater = LayoutInflater.from(parent!!.context)
        val view = inflater.inflate(R.layout.history_header, parent, false)
        view.findViewById<TextView>(R.id.tvHeader).text = headers[groupPosition]
        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val inflater = LayoutInflater.from(parent!!.context)
        val view = inflater.inflate(R.layout.history_body_row, parent, false)
        view.findViewById<TextView>(R.id.tvTitle).text = headers[groupPosition]
        view.findViewById<TextView>(R.id.tvBody).text = headers[groupPosition]
        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }
}