package com.example.telegram.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.telegram.domain.util.toEmojiFlag
import com.example.telegram.presentation.navigation.NavRoute
import com.example.telegram.presentation.viewModels.RegistrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneView(
    navController: NavController,
    viewModel: RegistrationViewModel
) {
    val selectedCountry by remember { mutableStateOf(viewModel.selectedCountry) }
    var enterPhone by remember { mutableStateOf(viewModel.selectedCountry?.code.toString()) }
    val focusManager = LocalFocusManager.current
    var isNumberConfirm by remember { mutableStateOf(false) }
    var numberIsNotCorrect by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(numberIsNotCorrect) {
        if (numberIsNotCorrect) {
            snackBarHostState.showSnackbar("Invalid phone number")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Your phone number")
            OutlinedTextField(
                value = if (selectedCountry == null) ""
                        else "${selectedCountry?.region?.toEmojiFlag()}  ${selectedCountry?.countryName}",
                onValueChange = { },
                label = { Text(text = "Country") },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .onFocusChanged {
                        if (it.isFocused) {
                            focusManager.clearFocus()
                            navController.navigate(NavRoute.PhoneSelectScreen.route)
                        }
                    }
            )
            OutlinedTextField(
                value = if (enterPhone == "null") "" else enterPhone,
                onValueChange = { enterPhone = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                modifier = Modifier
                    .padding(top = 20.dp),
                label = { Text(text = "Phone number") },
            )
            
            Button(
                onClick = {
                    isNumberConfirm = true
                },
                modifier = Modifier
                    .padding(10.dp),
                content = {
                    Text(text = "Next")
                }
            )

            if (isNumberConfirm) {
                AlertDialog(
                    onDismissRequest = { isNumberConfirm = false },
                    title = { Text(text = "Is this the correct number?") },
                    text = { Text(text = enterPhone) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                numberIsNotCorrect = viewModel.saveNumber(enterPhone, selectedCountry?.region)
                                isNumberConfirm = false
                                if (!numberIsNotCorrect) navController.navigate(NavRoute.UserNameScreen.route)
                            },
                            content = { Text(text = "Yes") },
                            modifier = Modifier
                        )
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { isNumberConfirm = false },
                            content = { Text(text = "Edit") },
                            modifier = Modifier
                        )
                    }
                )
            }
        }
    }
}