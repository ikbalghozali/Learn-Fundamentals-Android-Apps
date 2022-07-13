package com.ikbalghozali.githubuserapi.utils

import androidx.recyclerview.widget.DiffUtil
import com.ikbalghozali.githubuserapi.data.model.User


class DiffCallback(
    private val oldItems: ArrayList<User>,
    private val newItems: ArrayList<User>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].username == newItems[newItemPosition].username
    }

}