package com.ikbalghozali.githubuserapi.ui.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikbalghozali.githubuserapi.model.User
import com.ikbalghozali.githubuserapi.databinding.FragmentFollowBinding
import com.ikbalghozali.githubuserapi.ui.detail.UserDetailActivity
import com.ikbalghozali.githubuserapi.ui.main.ListUserAdapter

class FollowingFragment : Fragment(),
    ListUserAdapter.UserCallback {
    private var bindingNull: FragmentFollowBinding? = null
    private val binding get() = bindingNull!!
    private val userAdapter = ListUserAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingNull = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val extraUser =
            activity?.intent?.getParcelableExtra<User>(UserDetailActivity.EXTRA_USER) as User

        extraUser.login?.let { setupViewModel(it) }

        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingNull = null
    }

    private fun setupViewModel(username: String) {
        showLoading(true)
        val followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowViewModel::class.java)

        followViewModel.setFollowing(username)


        followViewModel.followLive.observe(viewLifecycleOwner) {
            if (it != null) {
                userAdapter.setData(it)
                showEmpty(false)
            }
            if (it.isEmpty()) {
                showEmpty(true)
            }
            showLoading(false)
        }
    }

    private fun showEmpty(state: Boolean) {
        binding.tvEmptyFollow.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarFollow.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        with(binding) {
            rvFollow.setHasFixedSize(true)
            rvFollow.layoutManager = LinearLayoutManager(requireContext())
            rvFollow.adapter = userAdapter
        }
    }

    override fun onUserClick(user: User) {
        val userDetailIntent = Intent(requireActivity(), UserDetailActivity::class.java)
        userDetailIntent.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(userDetailIntent)
    }
}