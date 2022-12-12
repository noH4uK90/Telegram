package com.example.telegram.presentation.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.telegram.presentation.navigation.BottomBar
import com.example.telegram.presentation.viewModels.ChatsViewModel

@Composable
fun SettingsView(
    navController: NavController,
    viewModel: ChatsViewModel
) {
    val bottomBarState = remember { mutableStateOf(true) }
    com.google.accompanist.insets.ui.Scaffold(
        bottomBar = { BottomBar(navController = navController, bottomBarState = bottomBarState) }
    ) {

    }
}