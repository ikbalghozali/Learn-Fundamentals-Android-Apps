package com.ikbalghozali.githubapps

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikbalghozali.githubapps.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)

        list.addAll(listUsers)
        showRecyclerList()
    }

    private val listUsers: ArrayList<User>
        get() {
            val dataAvatar = resources.obtainTypedArray(R.array.avatar)
            val dataName = resources.getStringArray(R.array.name)
            val dataUsername = resources.getStringArray(R.array.username)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataFollowers = resources.getStringArray(R.array.followers)
            val dataFollowing = resources.getStringArray(R.array.following)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataRepository = resources.getStringArray(R.array.repository)
            val listUser = ArrayList<User>()
            for (i in dataName.indices) {
                val user = User(
                    dataAvatar.getResourceId(i, -1),
                    dataName[i],
                    dataCompany[i],
                    dataUsername[i],
                    dataLocation[i],
                    dataRepository[i],
                    dataFollowers[i],
                    dataFollowing[i]
                )
                listUser.add(user)
            }
            return listUser
        }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
        }
        val listUserAdapter = UserListAdapter(list)
        binding.rvUsers.adapter = listUserAdapter
        listUserAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }

            override fun onItemShared(data: User) {
                sharedUser(data)
            }
        })
    }

    private fun showSelectedUser(data: User) {
        val moveObjectWithIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
        moveObjectWithIntent.putExtra(UserDetailActivity.EXTRA_USER, data)
        startActivity(moveObjectWithIntent)
    }

    private fun sharedUser(data: User) {
        val shareUser = "Github User:\n" +
                "Name: ${data.name}\n" +
                "Username: ${data.username}\n" +
                "Company: ${data.company}\n" +
                "Location: ${data.location}\n" +
                "Repositories: ${data.repository}\n" +
                "Followers: ${data.followers}\n" +
                "Following: ${data.following}"
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareUser)
        shareIntent.type = "text/html"
        startActivity(Intent.createChooser(shareIntent, "Share using"))
    }
}