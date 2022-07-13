package com.ikbalghozali.githubuserapi.ui.follow

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikbalghozali.githubuserapi.R
import com.ikbalghozali.githubuserapi.databinding.FragmentFollowBinding
import com.ikbalghozali.githubuserapi.data.model.User
import com.ikbalghozali.githubuserapi.ui.adapter.UserListAdapter
import com.ikbalghozali.githubuserapi.ui.detail.DetailUserActivity


class FollowersFragment : Fragment(R.layout.fragment_follow) {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowViewModel
    private lateinit var listAdapter: UserListAdapter
    private lateinit var username: String

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        listAdapter = UserListAdapter(ArrayList())
        listAdapter.notifyDataSetChanged()
        binding.apply {
            rvUserList.setHasFixedSize(true)
            rvUserList.layoutManager = LinearLayoutManager(activity)
            rvUserList.adapter = listAdapter
        }
        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowViewModel::class.java]
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                listAdapter.setList(it)
                showLoading(false)
            }

        }
        listAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(requireContext(), DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatarUrl)
                    startActivity(it)
                }
            }
        })


    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
