package com.dicoding.crimelessapps.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.crimelessapps.R
import kotlinx.android.synthetic.main.item_content.view.*

class Adapter(
    val urls: List<String>
) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_content,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = urls[position]
        Glide.with(holder.itemView).load(url).into(holder.itemView.ic_icon)
    }

    override fun getItemCount(): Int {
        return urls.size
    }
}

