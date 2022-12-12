package com.example.telegram.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.telegram.domain.models.UserInfo
import com.example.telegram.presentation.navigation.BottomBar
import com.example.telegram.presentation.navigation.NavRoute
import com.example.telegram.presentation.ui.topRectBorder
import com.example.telegram.presentation.viewModels.ChatsViewModel
import com.squareup.moshi.Moshi
import tgo1014.draggablescaffold.DraggableScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsView(
    navController: NavController,
    viewModel: ChatsViewModel
) {
    viewModel.loadFriends()
    val bottomBarState = remember { mutableStateOf(true) }
    Scaffold(
        bottomBar = { BottomBar(navController = navController, bottomBarState = bottomBarState) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            content = {
                viewModel.friendState.usersInfo?.let { userInfoList ->
                    userInfoList.map { userInfo ->
                        item {
                            DraggableScaffold(
                                contentUnderRight = {
                                    Button(
                                        onClick = {
                                            viewModel.deleteFriend(userInfo)
                                        },
                                        content = { Text(text = "Delete friend") },
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