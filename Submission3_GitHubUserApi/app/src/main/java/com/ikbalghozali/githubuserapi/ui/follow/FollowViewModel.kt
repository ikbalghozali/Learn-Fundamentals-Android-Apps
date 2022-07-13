package com.ikbalghozali.githubuserapi.ui.follow

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikbalghozali.githubuserapi.api.RetrofitClient
import com.ikbalghozali.githubuserapi.data.model.User
import com.ikbalghozali.githubuserapi.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    val listFollow = MutableLiveData<ArrayList<User>>()
    private val toast = MutableLiveData<Event<String>>()


    fun setListFollowers(username: String) {
        RetrofitClient.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollow.postValue(response.body())
                    } else {
                        toast.value = Event(response.toString())
                        Log.e(ContentValues.TAG, "onFailure ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e("Failed", t.message.toString())
                    toast.value = Event(t.message.toString())
                }
            })
    }

    fun getListFollowers(): LiveData<ArrayList<User>> {
        return listFollow
    }

    fun setListFollowing(username: String) {
        RetrofitClient.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollow.postValue(response.body())
                    } else {
                        toast.value = Event(response.toString())
                        Log.e(ContentValues.TAG, "onFailure ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e("Failed", t.message.toString())
                    toast.value = Event(t.message.toString())
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<User>> {
        return listFollow
    }

}