package com.ikbalghozali.githubuserapi.api

import com.ikbalghozali.githubuserapi.BuildConfig
import com.ikbalghozali.githubuserapi.data.model.DetailUser
import com.ikbalghozali.githubuserapi.data.model.User
import com.ikbalghozali.githubuserapi.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val KEY_TOKEN = BuildConfig.KEY_TOKEN
    }

    @GET("search/users")
    @Headers("Authorization: token $KEY_TOKEN")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $KEY_TOKEN")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $KEY_TOKEN")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $KEY_TOKEN")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>


}