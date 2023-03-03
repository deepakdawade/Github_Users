package com.dev2d.githubusers.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_users")
data class User(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0
)