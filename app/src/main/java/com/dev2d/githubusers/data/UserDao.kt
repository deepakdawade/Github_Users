package com.dev2d.githubusers.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(vararg user: User)

    @Query("SELECT * FROM github_users")
    fun allUsers(): Flow<List<User>>

    @Query("SELECT * FROM github_users WHERE login=:id LIMIT 1")
    fun getUser(id: String): Flow<User>

    @Query("DELETE FROM github_users")
    fun dropUsers()

}
