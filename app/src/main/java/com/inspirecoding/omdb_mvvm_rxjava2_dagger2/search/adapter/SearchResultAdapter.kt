package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.search.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.R
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.databinding.ItemMovieBinding
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Search
import com.squareup.picasso.Picasso

class SearchResultAdapter constructor (
    private val searchItems : ArrayList<Search>
) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    private val TAG = this.javaClass.simpleName
    private val NA = "N/A"

    fun updateItems(searchItems : ArrayList<Search>) {
        this.searchItems.clear()
        this.searchItems.addAll(searchItems)
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

            binding.tvTitle.text = searchItem.Title
            binding.tvYear.text = searchItem.Year

            searchItem.Poster?.let {
                Log.d(TAG, it)

                if(it != NA) {
                    Picasso.get()
                        .load(it)
                        .into(binding.ivPoster)
                }
            }

        }

    }

}