package com.ikbalghozali.githubuserapi.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikbalghozali.githubuserapi.R
import com.ikbalghozali.githubuserapi.data.model.User
import com.ikbalghozali.githubuserapi.databinding.ActivityMainBinding
import com.ikbalghozali.githubuserapi.ui.adapter.UserListAdapter
import com.ikbalghozali.githubuserapi.ui.detail.DetailUserActivity
import com.ikbalghozali.githubuserapi.ui.favorite.FavoriteActivity
import com.ikbalghozali.githubuserapi.ui.setting.SettingActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var listAdapter: UserListAdapter
    private var extraSearch: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
        binding.apply {
            rvUserList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUserList.setHasFixedSize(true)
        }
        showStartImage(true)
        viewModel.getSearchUsers().observe(this) {
            if (it != null) {
                listAdapter = UserListAdapter(it)
                binding.rvUserList.adapter = listAdapter
                listAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: User) {
                        Intent(this@MainActivity, DetailUserActivity::class.java).also { tk ->
                            tk.putExtra(DetailUserActivity.EXTRA_USERNAME, data.username)
                            tk.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                            tk.putExtra(DetailUserActivity.EXTRA_URL, data.avatarUrl)
                            startActivity(tk)
                        }
                    }
                })
                showLoading(false)
            }
            if (it.count() != 0) {
                showNotFound(false)
            } else {
                showNotFound(true)
            }
        }
        viewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showStartImage(state: Boolean) {
        binding.imgStart.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showNotFound(state: Boolean) {
        binding.notFound.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.maxWidth = Integer.MAX_VALUE

        if (extraSearch != null && extraSearch != "") {
            searchView.run {
                onActionViewExpanded()
                requestFocusFromTouch()
                setQuery(extraSearch, false)
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showStartImage(false)
                showNotFound(false)
                showLoading(true)
                viewModel.setSearchUsers(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                Intent(this@MainActivity, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.setting -> {
                Intent(this@MainActivity, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}