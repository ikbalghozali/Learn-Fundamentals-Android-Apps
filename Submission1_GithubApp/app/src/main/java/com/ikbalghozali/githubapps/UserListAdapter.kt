package com.ikbalghozali.githubapps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*

class UserListAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {
    private lateinit var onItemClickDetail: OnItemClickCallBack
    private lateinit var onItemClickShare: OnItemClickCallBack

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickDetail = onItemClickCallBack
        this.onItemClickShare = onItemClickCallBack
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (avatar, name, company) = listUser[position]
        Glide.with(holder.itemView.context)
            .load(avatar)
            .into(holder.ivAvatar)
        holder.tvName.text = name
        holder.tvCompany.text = company

        holder.itemView.setOnClickListener { onItemClickDetail.onItemClicked(listUser[holder.adapterPosition]) }
        holder.btnShare.setOnClickListener { onItemClickShare.onItemShared(listUser[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivAvatar: ImageView = itemView.findViewById(R.id.img_item_avatar)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvCompany: TextView = itemView.findViewById(R.id.tv_item_company)
        var btnShare: Button = itemView.findViewById(R.id.btn_share)
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: User)
        fun onItemShared(data: User)
    }

}