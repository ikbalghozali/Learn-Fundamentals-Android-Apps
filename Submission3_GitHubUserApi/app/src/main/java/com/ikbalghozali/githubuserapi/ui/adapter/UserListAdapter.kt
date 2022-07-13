package com.ikbalghozali.githubuserapi.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikbalghozali.githubuserapi.databinding.ItemUserBinding
import com.ikbalghozali.githubuserapi.data.model.User
import com.ikbalghozali.githubuserapi.utils.DiffCallback

class UserListAdapter(private var ListUser: List<User>) :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = ListUser[position]
        val photo = user.avatarUrl
        val username = user.username

        holder.binding.tvUsername.text = username

        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.binding.imgDetailAvatar)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(ListUser[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = ListUser.size


    @SuppressLint("NotifyDataSetChanged")
    fun setList(it: ArrayList<User>) {
        val diffUtil = DiffCallback(ListUser as ArrayList<User>, it)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        ListUser = it
        diffResults.dispatchUpdatesTo(this)
    }

    class ListViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }


}