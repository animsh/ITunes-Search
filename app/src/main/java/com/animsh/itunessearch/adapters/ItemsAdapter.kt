package com.animsh.itunessearch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animsh.itunessearch.databinding.LayoutItemContainerBinding
import com.animsh.itunessearch.models.ITunesResponse
import com.animsh.itunessearch.models.Result
import com.animsh.itunessearch.utils.SearchDiffUtil

/**
 * Created by animsh on 4/18/2021.
 */
class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.SearchViewHolder>() {

    private var searches = emptyList<Result>()

    class SearchViewHolder(private val binding: LayoutItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SearchViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemContainerBinding.inflate(layoutInflater, parent, false)
                return SearchViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder {
        return SearchViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentRecipe = searches[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return searches.size
    }

    fun setData(newData: ITunesResponse) {
        val searchDiffUtil = SearchDiffUtil(searches, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(searchDiffUtil)
        searches = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}