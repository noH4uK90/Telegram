package com.example.telegram.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.telegram.R
import com.example.telegram.domain.models.UserInfo
import com.example.telegram.presentation.navigation.BottomBar
import com.example.telegram.presentation.navigation.NavRoute
import com.example.telegram.presentation.states.MessageState
import com.example.telegram.presentation.ui.components.CustomTextField
import com.example.telegram.presentation.ui.components.MessageCard
import com.example.telegram.presentation.viewModels.ChatsViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatWithUserView(
    navController: NavController,
    userInfo: UserInfo?,
    viewModel: ChatsViewModel
) {
    val bottomBarState = remember { mutableStateOf(false) }
    userInfo?.let {
        com.google.accompanist.insets.ui.Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    modifier = Modifier,
                    navigationIcon = {
                        Icon(
                            painterResource(R.drawable.arrow_left),
                            contentDescription = "arrowBack",
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .clickable {
                                    navController.navigate(NavRoute.ChatsScreen.route)
                                }
                        )
                    },
                    title = {
                        Text(text = userInfo.login.username)
                    },
                    actions = {
                        Image(
                            rememberAsyncImagePainter(userInfo.picture?.medium),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .size(50.dp)
                                .clip(RoundedCornerShape(100))
                        )
                    }
                )
            },
            bottomBar = { BottomBar(navController = navController, bottomBarState = bottomBarState) }
        ) { paddingValues ->
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues)
            ) {
                val (messengerLazyColumn, messageInputTextField, sendButton, friendSendButton) = createRefs()

                var message by remember { mutableStateOf("") }
                var messages by rememberSaveable { mutableStateOf(listOf<MessageState?>(null)) }
                var isMine by remember { mutableStateOf(true) }

                LazyColumn(
                    modifier = Modifier
                        .constrainAs(messengerLazyColumn) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        },
                    content = {
                        items(items = messages) {
                            if (it != null)
                                MessageCard(message = it.message, isMine = it.isMine)
                        }
                    }
                )

                CustomTextField(
                    value = message,
                    onValueChanged = { message = it },
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(percent = 50)
                        )
                        .height(25.dp)
                        .width(310.dp)
                        .constrainAs(messageInputTextField) {
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        },
                    placeholderText = "Message"
                )
                IconButton(
                    onClick = {
                        if (message.isNotEmpty()) {
                            isMine = true
                            messages = messages + MessageState(message, true)
                            message = ""
                        }
                    },
                    modifier = Modifier
                        .constrainAs(sendButton) {
                            top.linkTo(messageInputTextField.top)
                            bottom.linkTo(messageInputTextField.bottom)
                            start.linkTo(messageInputTextField.end)
                            end.linkTo(parent.end)
                        },
                    content = {
                        Icon(Icons.Filled.Send, contentDescription = "")
                    }
                )
                IconButton(
                    onClick = {
                        isMine = false
                        messages = messages + MessageState(getRandomString(40), false)
                        message = ""
                    },
                    modifier = Modifier
                        .constrainAs(friendSendButton) {
                            top.linkTo(messageInputTextField.top)
                            bottom.linkTo(messageInputTextField.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(messageInputTextField.start)
                        },
                    content = {
                        Icon(Icons.Filled.Send, contentDescription = "")
                    }
                )
            }
        }
    }

}

fun getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}