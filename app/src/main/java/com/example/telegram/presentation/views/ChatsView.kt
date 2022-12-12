package com.example.telegram.presentation.views

import android.content.SharedPreferences
import android.icu.text.ListFormatter.Width
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.telegram.domain.models.UserInfo
import com.example.telegram.presentation.navigation.BottomBar
import com.example.telegram.presentation.navigation.NavRoute
import com.example.telegram.presentation.ui.topRectBorder
import com.example.telegram.presentation.viewModels.ChatsViewModel
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import tgo1014.draggablescaffold.DraggableScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsView(
    navController: NavController,
    viewModel: ChatsViewModel
) {
    val bottomBarState = remember { mutableStateOf(true) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { BottomBar(navController = navController, bottomBarState = bottomBarState) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            content = {
                viewModel.state.userInfoList?.let { userInfoList ->
                    userInfoList.items.map { userInfo ->
                        item {
                            DraggableScaffold(
                                contentUnderRight = {
                                    Button(
                                        onClick = {
                                            viewModel.addFriend(userInfo)
                                        },
                                        content = { Text(text = "Add friend") },
                                        modifier = Modifier
                                            .background(MaterialTheme.colorScheme.background)
                                    )
                                }
                            ) {
                                ConstraintLayout(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.background)
                                        .topRectBorder(brush = SolidColor(Color.LightGray))
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            val moshi = Moshi.Builder().build()
                                            val jsonAdapter = moshi.adapter(UserInfo::class.java).lenient()
                                            val userJson = jsonAdapter.toJson(userInfo)
                                            navController.navigate(NavRoute.ChatWithUserScreen.route + "?user=$userJson")
                                        }
                                ) {
                                    val (photoImage, userNameText) = createRefs()

                                    Image(
                                        rememberAsyncImagePainter(userInfo.picture?.medium),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(RoundedCornerShape(100))
                                            .constrainAs(photoImage) {
                                                start.linkTo(parent.start)
                                            }
                                    )
                                    Text(
                                        text = userInfo.login.username,
                                        modifier = Modifier
                                            .constrainAs(userNameText) {
                                                start.linkTo(photoImage.end, 10.dp)
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}