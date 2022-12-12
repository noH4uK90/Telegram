package com.example.telegram.presentation.viewModels

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.telegram.domain.dataBase.AppDataBase
import com.example.telegram.domain.dataBase.daos.UserDao
import com.example.telegram.domain.dataBase.entities.Friend
import com.example.telegram.domain.mappers.toUser
import com.example.telegram.domain.mappers.toUserInfo
import com.example.telegram.domain.models.UserInfo
import com.example.telegram.domain.repository.RandomUserRepository
import com.example.telegram.domain.util.Resource
import com.example.telegram.presentation.states.FriendState
import com.example.telegram.presentation.states.UsersState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    application: Application,
    private val repository: RandomUserRepository,
    private val sharedPreferences: SharedPreferences,
): BaseViewModel(application) {

    private var db: AppDataBase? = null

    var state by mutableStateOf(UsersState())
        private set

    var friendState by mutableStateOf(FriendState())
        private set

    fun loadFriends() {
        val currentUserString = sharedPreferences.getString("current_user", null) ?: return
        val currentUser = Gson().fromJson(currentUserString, UserInfo::class.java)

        db = AppDataBase.getAppDataBase(context)
        val friendId = db?.userDao()?.getFriendsId(currentUser.login.uuid)
        friendState = friendState.copy(
            usersInfo = friendId?.map { db!!.userDao().getUserById(it).toUserInfo() },
            isLoading = false,
            error = null
        )
    }

    fun loadUsers() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            when (val result = repository.getUser()) {
                is Resource.Success -> {
                    state = state.copy(
                        userInfoList = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        userInfoList = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
            viewModelScope.cancel()
        }
    }

    fun getUsersFromDb(): List<UserInfo>? {
        return try {
            db = AppDataBase.getAppDataBase(context)
            db?.userDao()?.getUsers()?.map { it.toUserInfo() }
        } catch (e: Exception) {
            Log.i("ERROR", e.message.toString())
            null
        }
    }

    fun addUserToDb(userInfo: UserInfo) {
        try {
            db = AppDataBase.getAppDataBase(context)
            db?.userDao()?.insertUser(userInfo.toUser())
        } catch (e: Exception) {
            Log.i("ERROR", e.message.toString())
        }
    }

    fun addFriend(addedUser: UserInfo) {
        try {
            val currentUserString = sharedPreferences.getString("current_user", null) ?: return
            val currentUser = Gson().fromJson(currentUserString, UserInfo::class.java)

            db = AppDataBase.getAppDataBase(context)
            val users = getUsersFromDb()
            if (users?.any { it.login.uuid == addedUser.login.uuid } == false) {
                addUserToDb(addedUser)
            }
            db?.userDao()?.insertFriend(Friend(currentUser.login.uuid, addedUser.login.uuid))
            loadFriends()
        } catch (e: Exception) {
            Log.i("ERROR", e.message.toString())
        }
    }

    fun deleteFriend(deletedUser: UserInfo) {
        try {
            val currentUserString = sharedPreferences.getString("current_user", null) ?: return
            val currentUser = Gson().fromJson(currentUserString, UserInfo::class.java)

            db = AppDataBase.getAppDataBase(context)
            db?.userDao()?.deleteFriend(Friend(currentUser.login.uuid, deletedUser.login.uuid))
            db?.userDao()?.deleteUser(deletedUser.toUser())
            loadFriends()
        } catch (e: Exception) {
            Log.i("ERROR", e.message.toString())
        }
    }
}