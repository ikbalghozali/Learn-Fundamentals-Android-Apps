package com.ikbalghozali.githubapps

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ikbalghozali.githubapps.databinding.ActivityUserDetailBinding


class UserDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var Nama: String
    private lateinit var Username: String
    private lateinit var Details: String
    private lateinit var Followers: String
    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = "Detail User"
        }
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val image = user.avatar
        Nama = user.name
        Username = user.username
        Followers = "${user.followers} Followers ." + " ${user.following} Following"
        Details = "${user.name}\n" +
                "${user.location}\n" +
                "${user.company}\n" +
                user.repository

        Glide.with(this).load(image).circleCrop().into(binding.ivAvatarReceived)
        binding.tvNameReceived.text = Nama
        binding.tvObjectReceived.text = Details
        binding.tvUsernameReceived.text = Username
        binding.tvFollowReceived.text = Followers
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun follloUser(view: View) {
        Toast.makeText(this@UserDetailActivity, "You have followed ${Username}", Toast.LENGTH_SHORT)
            .show()
    }

}