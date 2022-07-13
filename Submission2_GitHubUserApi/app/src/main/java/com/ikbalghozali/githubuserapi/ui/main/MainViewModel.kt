package com.ikbalghozali.githubuserapi.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikbalghozali.githubuserapi.model.UserResponse
import com.ikbalghozali.githubuserapi.model.User
import com.ikbalghozali.githubuserapi.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _users = MutableLiveData<ArrayList<User>>()
    val users: LiveData<ArrayList<User>> = _users

    fun searchUsers(query: String) {
        val client = RetrofitClient.apiInstance.getSearchUsers(query)

        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    _users.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("MainViewModel", "onFailure searchUsers ${t.message}")
            }
        })
    }
}