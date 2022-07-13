package com.ikbalghozali.githubuserapi.data.model

import com.google.gson.annotations.SerializedName

data class User(

    val id: Int?,
    @field:SerializedName("login")
    val username: String?,
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,
)