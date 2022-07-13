package com.ikbalghozali.githubuserapi.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.ikbalghozali.githubuserapi.R
import com.ikbalghozali.githubuserapi.databinding.ActivityUserDetailBinding
import com.ikbalghozali.githubuserapi.model.DetailUser
import com.ikbalghozali.githubuserapi.model.User
import com.ikbalghozali.githubuserapi.ui.follow.SectionPagerAdapter

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.user_detail)
        supportActionBar?.elevation = 0f

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        user.login?.let { setupViewModel(it) }
        setupViewPager()


        binding.btFollow.setOnClickListener{
            val pressed = "Follow"
            if (binding.btFollow.text == pressed){
                binding.btFollow.text = getString(R.string.unfollow)
                Toast.makeText(this@UserDetailActivity, "You have followed ${user.login}", Toast.LENGTH_SHORT)
                    .show()
            }
            else{
                binding.btFollow.text = getString(R.string.follow)
                Toast.makeText(this@UserDetailActivity, "You Unfollow ${user.login}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateDetail(user: DetailUser) {
        with(binding) {
            Glide.with(this@UserDetailActivity)
                .load(user.avatar_url)
                .centerCrop()
                .placeholder(R.drawable.profile_picture)
                .into(imgDetailAvatar)
            tvDetailName.text = user.name ?: " -"
            tvDetailUsername.text = user.login ?: " -"
            tvDetailFollower.text = "Followers ${user.followers} • Following ${user.following}"
            tvDetailBio.text = user.bio
            tvDetailCompany.text = user.company ?: " -"
            tvDetailLocation.text = user.location ?: " -"
            tvDetailRepository.text = "Repositories ${user.public_repos}"
        }
    }

    private fun setupViewModel(username: String) {
        showLoading(true)
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        detailViewModel.setUserDetail(username)

        detailViewModel.userDetailLive.observe(this) {
            populateDetail(it)
            showLoading(false)
        }
    }

    private fun setupViewPager() {
        val pagerAdapter = SectionPagerAdapter(this)
        val viewPager = binding.viewPagerDetail
        viewPager.adapter = pagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarDetail.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower_user,
            R.string.following_user
        )
    }
}