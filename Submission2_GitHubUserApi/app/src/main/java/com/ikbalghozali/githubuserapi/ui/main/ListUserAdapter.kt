package com.ikbalghozali.githubuserapi.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikbalghozali.githubuserapi.R
import com.ikbalghozali.githubuserapi.model.User
import com.ikbalghozali.githubuserapi.databinding.ItemUserBinding

class ListUserAdapter(private val callback: UserCallback) :
    RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {
    private val mData = ArrayList<User>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(users: ArrayList<User>) {
        mData.clear()
        mData.addAll(users)
        notifyDataSetChanged()
    }

    interface UserCallback {
        fun onUserClick(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val userBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .centerCrop()
                    .placeholder(R.drawable.profile_picture)
                    .into(imgDetailAvatar)
                tvName.text = user.login
                tvType.text = user.type
                root.setOnClickListener { callback.onUserClick(user) }
            }
        }
    }
}