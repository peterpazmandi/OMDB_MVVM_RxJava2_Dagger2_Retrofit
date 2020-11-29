package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.search.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.R
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.databinding.ItemMovieBinding
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Search
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.search.SearchFragmentDirections

class SearchResultAdapter constructor (
) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    private val TAG = this.javaClass.simpleName
    private val NA = "N/A"

    private var searchItems = ArrayList<Search>()

    lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(movie : Search, imageView: ImageView, textView: TextView)
    }

    fun updateItems (newList : ArrayList<Search>) {
        if (newList != null) {
            if (searchItems.isNotEmpty())
                searchItems.removeAt(searchItems.size - 1)
            searchItems.clear()
            searchItems.addAll(newList)
        } else {
            searchItems.add(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemMovieBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.item_movie, parent, false
        )
        return SearchResultViewHolder(binding)
    }

    override fun getItemCount() = searchItems.size

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(searchItem = searchItems[position])
    }

    inner class SearchResultViewHolder (val binding : ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (searchItem : Search) {

            binding.ivPoster.transitionName = searchItem.Poster
            binding.tvTitle.transitionName = searchItem.Title

            binding.tvTitle.text = searchItem.Title
            binding.tvYear.text = searchItem.Year

            searchItem.Poster?.let {
                Log.d(TAG, it)

                Glide
                    .with(binding.root)
                    .load(it)
                    .centerCrop()
                    .placeholder(R.drawable.image_not_found)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.ivPoster)
            }

            binding.mcvItemLayout.setOnClickListener {
                listener.onItemClick(searchItem, binding.ivPoster, binding.tvTitle)
            }

        }

    }

}