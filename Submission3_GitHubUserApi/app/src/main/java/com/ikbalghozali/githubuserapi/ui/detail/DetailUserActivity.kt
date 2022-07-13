package com.ikbalghozali.githubuserapi.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.ikbalghozali.githubuserapi.R
import com.ikbalghozali.githubuserapi.databinding.ActivityDetailUserBinding
import com.ikbalghozali.githubuserapi.ui.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower_user,
            R.string.following_user
        )
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.user_detail)
            elevation = 0f
        }
        showLoading(true)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        showTabLayout()
        bundle.putString(EXTRA_USERNAME, username)
        viewModel = ViewModelProvider(
            this
        )[DetailUserViewModel::class.java]
        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvDetailName.text = it.name ?: " -"
                    tvDetailUsername.text = it.username ?: " -"
                    tvDetailBio.text = it.bio
                    tvDetailLocation.text = it.location ?: " -"
                    tvDetailCompany.text = it.company ?: " -"
                    tvDetailRepository.text = "Repositories ${it.public_repos}"
                    tvDetailFollower.text = "Followers ${it.followers} • Following ${it.following}"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .circleCrop()
                        .into(imgDetailAvatar)
                }
                showLoading(false)
            }
        }
        viewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                viewModel.addToFavorite(username.toString(), id, avatarUrl.toString())
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = isChecked
        }

    }

    private fun showTabLayout(){
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        val sectionsPagerAdapter = SectionPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewPagerDetail
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
    private fun showLoading(state: Boolean) {
        binding.progressBarDetail.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}