package com.ikbalghozali.githubuserapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int? = null,
    val login: String? = null,
    val type: String? = null,
    val avatar_url: String? = null
) : Parcelable