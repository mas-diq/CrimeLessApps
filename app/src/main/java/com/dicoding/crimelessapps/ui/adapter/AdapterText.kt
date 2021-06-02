package com.dicoding.crimelessapps.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.crimelessapps.R
import com.dicoding.crimelessapps.ui.Data.DataNotif
import kotlinx.android.synthetic.main.item_content.view.*

class AdapterText(private val contents: List<DataNotif>) :
    RecyclerView.Adapter<AdapterText.ContentHolder>() {
    inner class ContentHolder(view: View) : RecyclerView.ViewHolder(view) {

        val icons = view.ic_icon!!
        val status = view.tv_item_status!!
        val desc = view.tv_item_desc!!
        val time = view.tv_item_clock!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentHolder {
        return ContentHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_content, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContentHolder, position: Int) {
        val contents = contents[position]
        Glide.with(holder.itemView.context)
            .load(contents.icon)
            .apply(RequestOptions())
            .into(holder.icons)
        holder.status.text = contents.title
        holder.desc.text = contents.desc
        holder.time.text = contents.time
    }

    override fun getItemCount(): Int {
        return contents.size
    }
}