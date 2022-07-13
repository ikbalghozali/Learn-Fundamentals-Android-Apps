package com.ikbalghozali.githubuserapi.ui.main


import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.ikbalghozali.githubuserapi.api.RetrofitClient
import com.ikbalghozali.githubuserapi.data.model.User
import com.ikbalghozali.githubuserapi.data.model.UserResponse
import com.ikbalghozali.githubuserapi.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<List<User>>()

    private val toast = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = toast

    fun setSearchUsers(query: String) {
        RetrofitClient.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.value = response.body()?.items
                    } else {
                        toast.value = Event(response.message())
                        Log.e(TAG, "onFailure ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    toast.value = Event("No internet connection")
                    Log.e("Failed", t.message.toString())
                }

            })
    }

    fun getSearchUsers(): LiveData<List<User>> {
        return listUsers
    }


}