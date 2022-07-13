package com.ikbalghozali.githubuserapi.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ikbalghozali.githubuserapi.data.local.FavoriteUser
import com.ikbalghozali.githubuserapi.data.local.FavoriteUserDao
import com.ikbalghozali.githubuserapi.data.local.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: FavoriteUserDao?

    companion object {
        private lateinit var userDb: UserDatabase
    }

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}