package com.ikbalghozali.githubapps

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var avatar: Int,
    var name: String,
    var company: String,
    var username: String,
    var location: String,
    var repository: String,
    var followers: String,
    var following: String
) : Parcelable