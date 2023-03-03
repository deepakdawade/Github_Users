package com.dev2d.githubusers.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "github_users")
data class User(

    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String = "",

    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String = "",

    @SerializedName("followers_url")
    @ColumnInfo(name = "followers_url")
    val followersUrl: String = "",

    @SerializedName("following_url")
    @ColumnInfo(name = "following_url")
    val followingUrl: String = "",

    @SerializedName("gists_url")
    @ColumnInfo(name = "gists_url")
    val gistsUrl: String = "",

    @SerializedName("starred_url")
    @ColumnInfo(name = "starred_url")
    val starredUrl: String = "",

    @SerializedName("subscriptions_url")
    @ColumnInfo(name = "subscriptions_url")
    val subscriptionsUrl: String = "",

    @SerializedName("organizations_url")
    @ColumnInfo(name = "organizations_url")
    val organizationsUrl: String = "",

    @SerializedName("repos_url")
    @ColumnInfo(name = "repos_url")
    val reposUrl: String = "",

    @SerializedName("events_url")
    @ColumnInfo(name = "events_url")
    val eventsUrl: String = "",

    @SerializedName("received_events_url")
    @ColumnInfo(name = "received_events_url")
    val receivedEventsUrl: String = "",

    @SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String = "",

    @SerializedName("login")
    @ColumnInfo(name = "login")
    val login: String = "",
    @SerializedName("node_id")
    @ColumnInfo(name = "node_id")
    val nodeId: String = "",

    @SerializedName("site_admin")
    @ColumnInfo(name = "site_admin")
    val siteAdmin: Boolean = false,

    )
