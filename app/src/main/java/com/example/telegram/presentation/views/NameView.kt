package com.example.telegram.presentation.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.telegram.presentation.navigation.NavRoute
import com.example.telegram.presentation.viewModels.RegistrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameView(
    navController: NavController,
    viewModel: RegistrationViewModel
) {

    var nameIsCorrectCorrect by remember { mutableStateOf(true) }
    val snackBarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(nameIsCorrectCorrect) {
        if (!nameIsCorrectCorrect) {
            snackBarHostState.showSnackbar("Both fields must be filled in and be at least 2 characters long")
            nameIsCorrectCorrect = true
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {

                var firstName by remember { mutableStateOf("") }
                var lastName by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text(text = "First name") },
                    modifier = Modifier,
                    singleLine = true
                )

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text(text = "Last name") },
                    modifier = Modifier
                        .padding(top = 20.dp),
                    singleLine = true
                )
                
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        Log.i("PHONE", viewModel.userPhone)
                        nameIsCorrectCorrect = viewModel.createUser(firstName, lastName)
                        if (nameIsCorrectCorrect)
                            navController.navigate(NavRoute.ChatsScreen.route)
                    },
                    content = { Text(text = "Save") }
                )
            }
        )
    }
}