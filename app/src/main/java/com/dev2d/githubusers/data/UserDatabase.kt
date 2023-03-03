package com.dev2d.githubusers.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.dev2d.githubusers.util.toDataClass
import com.dev2d.githubusers.util.toJsonString

@Database(entities = [User::class], version = 1)
@TypeConverters(DatabaseTypeConvetor::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}


object DatabaseTypeConvetor {
    @TypeConverter
    fun fromString(value: String): List<String> = value.toDataClass()

    @TypeConverter
    fun fromList(list: List<String>): String = list.toJsonString()
}