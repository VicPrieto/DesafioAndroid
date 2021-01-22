package com.desafioandroid.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desafioandroid.R
import com.desafioandroid.model.Posting

class MainAdapter(private var listener: OnPostClickListener) :
    RecyclerView.Adapter<MainAdapter.ItemViewHolder>() {

    var postingAdapterList: MutableList<Posting> = mutableListOf<Posting>()

    interface OnPostClickListener {
        fun postClick(position: Int)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val tvValue: TextView = itemView.findViewById(R.id.tvValue)
        val tvOrigin: TextView = itemView.findViewById(R.id.tvOrigin)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (RecyclerView.NO_POSITION != position) {
                listener.postClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_posting, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val posting = postingAdapterList[position]
        holder.tvValue.text = posting.valor.toString()
        holder.tvOrigin.text = posting.origem
    }

    override fun getItemCount() = postingAdapterList.size
}