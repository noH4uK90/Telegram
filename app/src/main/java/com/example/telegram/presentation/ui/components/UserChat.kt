package com.example.telegram.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.telegram.domain.models.UserInfo
import com.example.telegram.presentation.states.UsersState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserChat(user: UserInfo) {
    user.let { userInfo ->
        Scaffold() { paddingValues ->
            ConstraintLayout(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                val (photoImage, userNameText) = createRefs()

                Image(
                    rememberImagePainter(userInfo.picture?.medium),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(100))
                )
            }
        }
    }
}