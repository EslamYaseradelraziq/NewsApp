package com.example.news_api.ui.main.News

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news_api.Api.model.NewsResponse.News
import com.example.news_api.R
import com.example.news_api.databinding.ItemNewsBinding

class NewsAdapter(var items: List<News?>?) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val ViewBinding =
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(ViewBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.Binding.author.text = item?.author
        holder.Binding.title.text = item?.title
        holder.Binding.time.text = item?.publishedAt
        Glide.with(holder.itemView)
            .load(item?.urlToImage)
            .placeholder(R.drawable.ic_image)
            .into(holder.Binding.image)

    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    fun changData(articles: List<News?>?) {
        items = articles
        notifyDataSetChanged()
    }


    class ViewHolder(val Binding: ItemNewsBinding) : RecyclerView.ViewHolder(Binding.root)
}