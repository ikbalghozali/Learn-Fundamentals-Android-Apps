package com.ikbalghozali.githubuserapi.ui.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikbalghozali.githubuserapi.model.User
import com.ikbalghozali.githubuserapi.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val follow = MutableLiveData<ArrayList<User>>()
    val followLive: LiveData<ArrayList<User>> = follow

    fun setFollowers(username: String) {
        val client = RetrofitClient.apiInstance.getFollowersUsers(username)

        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    follow.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.e("FollowViewModel", "onFailure setFollowers ${t.message}")
            }
        })
    }

    fun setFollowing(username: String) {
        val client = RetrofitClient.apiInstance.getFollowingUsers(username)

        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    follow.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.e("FollowViewModel", "onFailure setFollowers ${t.message}")
            }
        })
    }
}