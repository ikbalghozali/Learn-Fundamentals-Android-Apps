package com.ikbalghozali.githubuserapi.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikbalghozali.githubuserapi.api.RetrofitClient
import com.ikbalghozali.githubuserapi.model.DetailUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val userDetail = MutableLiveData<DetailUser>()
    val userDetailLive: LiveData<DetailUser> = userDetail

    fun setUserDetail(username: String) {
        val client = RetrofitClient.apiInstance.getDetailUsers(username)

        client.enqueue(object : Callback<DetailUser> {
            override fun onResponse(
                call: Call<DetailUser>,
                response: Response<DetailUser>
            ) {
                if(response.isSuccessful){
                    userDetail.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                Log.e("DetailViewModel", "onFailure setUserDetail ${t.message}")
            }
        })
    }
}