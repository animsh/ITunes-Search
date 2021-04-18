package com.animsh.itunessearch.utils

import androidx.recyclerview.widget.DiffUtil
import com.animsh.itunessearch.models.Result

/**
 * Created by animsh on 4/18/2021.
 */
class SearchDiffUtil(
    private val oldList: List<Result>,
    private val newList: List<Result>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}