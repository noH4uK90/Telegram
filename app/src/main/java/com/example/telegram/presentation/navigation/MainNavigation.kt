package com.example.telegram.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.example.telegram.R
import com.example.telegram.domain.models.UserInfo
import com.example.telegram.presentation.viewModels.ChatsViewModel
import com.example.telegram.presentation.viewModels.RegistrationViewModel
import com.example.telegram.presentation.views.*
import com.google.accompanist.insets.ui.BottomNavigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.squareup.moshi.Moshi

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    registrationViewModel: RegistrationViewModel = hiltViewModel(),
    chatsViewModel: ChatsViewModel = hiltViewModel(),
    navController: NavHostController = rememberAnimatedNavController(),
    startDestination: String,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    when (navBackStackEntry?.destination?.route) {
        "phoneScreen" -> bottomBarState.value = false
        "phoneSelectScreen" -> bottomBarState.value = false
        "registrationScreen" -> bottomBarState.value = false
        "chatsScreen" -> bottomBarState.value = true
        "contactsScreen" -> bottomBarState.value = true
        "settingsScreen" -> bottomBarState.value = true
        "userChatScreen" -> bottomBarState.value = false
    }

    com.google.accompanist.insets.ui.Scaffold(
        content = {
            AnimatedNavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable(NavRoute.PhoneScreen.route) { backStackEntry ->
                    PhoneView(navController, registrationViewModel)
                }

                composable(NavRoute.PhoneSelectScreen.route,
                    enterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearEasing
                            )
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            towards = AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearEasing
                            )
                        )
                    }
                ) { backStackEntry ->
                    SearchCountryView(navController, registrationViewModel)
                }

                composable(NavRoute.UserNameScreen.route) { backStackEntry ->
                    NameView(navController, registrationViewModel)
                }

                composable(NavRoute.ChatsScreen.route) { backStackEntry ->
                    ChatsView(navController, chatsViewModel)
                }

                composable(NavRoute.ChatWithUserScreen.route.plus("?user={user}")) { backStackEntry ->
                    val userJson = backStackEntry.arguments?.getString("user")
                    val moshi = Moshi.Builder().build()
                    val jsonAdapter = moshi.adapter(UserInfo::class.java).lenient()
                    val userObject = jsonAdapter.fromJson(userJson)
                    ChatWithUserView(navController, userObject, chatsViewModel)
                }
                composable(NavRoute.ContactsScreen.route) { backStackEntry ->
                    ContactsView(navController, chatsViewModel)
                }
                composable(NavRoute.SettingsScreen.route) { backStackEntry ->
                    SettingsView(navController, chatsViewModel)
                }
            }
        }
    )
}

@Composable
fun BottomBar(navController: NavController, bottomBarState: MutableState<Boolean>) {
    val items = listOf(
        NavRoute.ContactsScreen,
        NavRoute.ChatsScreen,
        NavRoute.SettingsScreen
    )

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title
                            )
                        },
                        label = { Text(text = item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    )
}

sealed class NavRoute(val route: String, val icon: Int = 0, val title: String = "") {
    object PhoneScreen: NavRoute("phoneScreen")
    object PhoneSelectScreen: NavRoute("phoneSelectScreen")
    object UserNameScreen: NavRoute("registrationScreen")
    object ChatsScreen: NavRoute("chatsScreen", R.drawable.chats, "Chats")
    object ContactsScreen: NavRoute("contactsScreen", R.drawable.contacts, "Contacts")
    object SettingsScreen: NavRoute("settingsScreen", R.drawable.cogs, "Settings")
    object ChatWithUserScreen: NavRoute("userChatScreen")
}