package com.ikbalghozali.githubuserapi.data.model

import com.google.gson.annotations.SerializedName

data class DetailUser(
    @field:SerializedName("login")
    val username: String? = null,
    val id: Int? = null,
    val avatar_url: String? = null,
    val followers_url: String? = null,
    val following_url: String? = null,
    val name: String? = null,
    val bio: String? = null,
    val following: Int? = null,
    val followers: Int? = null,
    val public_repos: String? = null,
    val company: String? = null,
    val location: String? = null
)
