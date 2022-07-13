package com.ikbalghozali.githubuserapi.api

import com.ikbalghozali.githubuserapi.model.UserResponse
import com.ikbalghozali.githubuserapi.model.User
import com.ikbalghozali.githubuserapi.model.DetailUser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String,
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUsers(
        @Path("username") username: String,
    ): Call<DetailUser>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUsers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUsers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>
}