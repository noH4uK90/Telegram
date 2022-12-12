package com.example.telegram.presentation

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgs
import com.example.telegram.domain.dataBase.AppDataBase
import com.example.telegram.presentation.navigation.MainNavigation
import com.example.telegram.presentation.navigation.NavRoute
import com.example.telegram.presentation.ui.theme.TelegramTheme
import com.example.telegram.presentation.viewModels.ChatsViewModel
import com.example.telegram.presentation.viewModels.RegistrationViewModel
import com.example.telegram.presentation.views.ChatWithUserView
import com.example.telegram.presentation.views.PhoneView
import com.example.telegram.presentation.views.SearchCountryView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val chatsViewModel: ChatsViewModel by viewModels()
    private val registrationViewModel: RegistrationViewModel by viewModels()

    private var startDestination: String = NavRoute.PhoneScreen.route

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatsViewModel.loadUsers()

        setContent {
            TelegramTheme {
                startDestination = if (registrationViewModel.checkForExistUser()) {
                    NavRoute.ChatsScreen.route
                } else NavRoute.PhoneScreen.route
                MainNavigation(startDestination = startDestination)
            }
        }
    }
}