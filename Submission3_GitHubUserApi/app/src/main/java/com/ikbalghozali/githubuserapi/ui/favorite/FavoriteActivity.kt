package com.ikbalghozali.githubuserapi.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikbalghozali.githubuserapi.data.local.FavoriteUser
import com.ikbalghozali.githubuserapi.data.model.User
import com.ikbalghozali.githubuserapi.databinding.ActivityFavoriteBinding
import com.ikbalghozali.githubuserapi.ui.adapter.UserListAdapter
import com.ikbalghozali.githubuserapi.ui.detail.DetailUserActivity


class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var listAdapter: UserListAdapter
    private lateinit var viewModel: FavoriteViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listAdapter = UserListAdapter(ArrayList())
        listAdapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        listAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUserList.setHasFixedSize(true)
            rvUserList.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUserList.adapter = listAdapter
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                listAdapter.setList(list)
            }
        }

        supportActionBar?.apply {
            title = "Favorite"
            elevation = 0f
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.id, user.login, user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

}