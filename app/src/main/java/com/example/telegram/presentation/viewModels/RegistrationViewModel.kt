package com.example.telegram.presentation.viewModels

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.telegram.domain.dataBase.AppDataBase
import com.example.telegram.domain.dataBase.daos.UserDao
import com.example.telegram.domain.mappers.toUser
import com.example.telegram.domain.models.Login
import com.example.telegram.domain.models.Name
import com.example.telegram.domain.models.PhoneCode
import com.example.telegram.domain.models.UserInfo
import com.google.gson.Gson
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    application: Application,
    private val sharedPreferences: SharedPreferences
): BaseViewModel(application) {

    private var db: AppDataBase? = null
    private var userDao: UserDao? = null

    var userPhone: String = ""
    var selectedCountry: PhoneCode? = null

    fun selectCountry(navController: NavController, country: PhoneCode, route: String) {
        selectedCountry = country
        navController.navigate(route)
    }

    fun saveNumber(number: String, code: String?): Boolean {
        return try {
            val phoneNumberUtils = PhoneNumberUtil.getInstance()
            userPhone = number
            !phoneNumberUtils.isPossibleNumber(number, code)
        } catch(e: Exception) {
            false
        }
    }

    fun createUser(first: String, last: String?): Boolean {
        return try {
            if (first.length < 2) false
            else {
                val registeredUserInfo = UserInfo(
                    login = Login(
                        uuid = UUID.randomUUID().toString(),
                        username = "$first${last?.replaceFirstChar{ it.uppercaseChar() }}".lowercase()),
                    name = Name(
                        first = first,
                        last = last),
                    email = null,
                    gender = null,
                    phone = userPhone,
                    picture = null
                )
                val currentUser = Gson().toJson(registeredUserInfo)
                db = AppDataBase.getAppDataBase(context)
                userDao = db?.userDao()
                val registeredUser = registeredUserInfo.toUser()
                userDao?.insertUser(registeredUser)
                sharedPreferences.edit().putString("current_user", currentUser).apply()
                true
            }
        } catch(e: Exception) {
            Log.w("DB", e.toString())
            false
        }
    }

    fun deleteUser() {
        sharedPreferences.edit().putString("current_user", null).apply()
        db = AppDataBase.getAppDataBase(context)
        AppDataBase.destroyDataBase()
    }

    fun checkForExistUser(): Boolean {
        return sharedPreferences.getString("current_user", null) != null
    }
}