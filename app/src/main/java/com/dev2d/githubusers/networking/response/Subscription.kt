package com.dev2d.githubusers.networking.response

import com.google.gson.annotations.SerializedName

data class Subscription(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("full_name")
    val full_name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("owner")
    val owner: Owner,

    ) {
    data class Owner(
        @SerializedName("avatar_url")
        val avatarUrl: String,

        @SerializedName("login")
        val login: String,

        )
}
