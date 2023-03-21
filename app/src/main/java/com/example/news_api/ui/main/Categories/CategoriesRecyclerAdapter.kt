package com.example.news_api.ui.main.Categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.news_api.databinding.ItemCategoryBinding

class CategoriesRecyclerAdapter(val items: List<Category>) :
    RecyclerView.Adapter<CategoriesRecyclerAdapter.ViewHolder>() {
    class ViewHolder(val itemBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.image.setImageResource(items[position].imageId)
        holder.itemBinding.container.setBackgroundColor(
            ContextCompat.getColor(holder.itemView.context, items[position].backgroundColorId)
        )
        holder.itemBinding.title.text = items[position].name
        onItemClickListener?.let { clickListener ->
            holder.itemBinding.container.setOnClickListener {
                clickListener.onItemClick(position, item = items[position])
            }
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(pos: Int, item: Category)
    }
}