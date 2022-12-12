package com.example.telegram.domain.dataBase.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.telegram.domain.dataBase.entities.Friend
import com.example.telegram.domain.dataBase.entities.User
import com.example.telegram.domain.models.UserInfo

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM users")
    fun getUsers(): List<User>

    @Query("SELECT * FROM users WHERE uuid == :userId")
    fun getUserById(userId: String): User

    @Query("SELECT friendId FROM friend WHERE userId == :userId")
    fun getFriendsId(userId: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFriend(friend: Friend)

    @Delete
    fun deleteFriend(friend: Friend)

    @Delete
    fun deleteUser(user: User)
}