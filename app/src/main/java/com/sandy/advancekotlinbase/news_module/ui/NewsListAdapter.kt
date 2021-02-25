package com.sandy.advancekotlinbase.news_module.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sandy.advancekotlinbase.R
import com.sandy.advancekotlinbase.databinding.ItemNewsBinding
import com.sandy.advancekotlinbase.news_module.models.NewsArticlesModel
import com.sandy.advancekotlinbase.news_module.view_models.NewsItemViewModel
import com.squareup.picasso.Picasso

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    private lateinit var newsList: List<NewsArticlesModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemNewsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_news,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return if (::newsList.isInitialized) newsList.size else 0
    }

    fun updateNewsList(newsList: List<NewsArticlesModel>) {
        this.newsList = newsList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = NewsItemViewModel()

        fun bind(model: NewsArticlesModel) {
            viewModel.bind(model)
            binding.viewModel = viewModel
            Picasso.get().load(model.urlToImage).placeholder(R.drawable.ic_no_image)
                .into(binding.img)
        }
    }
}