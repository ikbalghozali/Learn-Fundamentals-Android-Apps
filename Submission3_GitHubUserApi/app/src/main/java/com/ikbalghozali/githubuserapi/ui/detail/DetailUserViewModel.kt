package com.ikbalghozali.githubuserapi.ui.detail

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ikbalghozali.githubuserapi.api.RetrofitClient
import com.ikbalghozali.githubuserapi.data.local.FavoriteUser
import com.ikbalghozali.githubuserapi.data.local.FavoriteUserDao
import com.ikbalghozali.githubuserapi.data.local.UserDatabase
import com.ikbalghozali.githubuserapi.data.model.DetailUser
import com.ikbalghozali.githubuserapi.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUser>()
    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)

    private val toast = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = toast

    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUser> {
                override fun onResponse(
                    call: Call<DetailUser>,
                    response: Response<DetailUser>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    } else {
                        Log.e(TAG, "onFailure ${response.message()}")
                        toast.value = Event(response.toString())
                    }
                }
                override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                    Log.e("Failed", t.message.toString())
                    toast.value = Event("No internet connection")
                    toast.value = Event(t.message.toString())
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUser> {
        return user
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}