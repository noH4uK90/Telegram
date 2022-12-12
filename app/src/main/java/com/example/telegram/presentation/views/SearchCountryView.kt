package com.example.telegram.presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.telegram.R
import com.example.telegram.domain.util.phoneCodes
import com.example.telegram.domain.util.toEmojiFlag
import com.example.telegram.presentation.navigation.NavRoute
import com.example.telegram.presentation.viewModels.RegistrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCountryView(
    navController: NavController,
    viewModel: RegistrationViewModel
) {

    var searchCountry by remember { mutableStateOf("") }
    var isSearchNow by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Scaffold(
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
                                if (isSearchNow) isSearchNow = false
                                else navController.navigate(NavRoute.PhoneScreen.route)
                            }
                    )
                },
                title = {
                    if (isSearchNow) {
                        OutlinedTextField(
                            value = searchCountry,
                            onValueChange = { searchCountry = it },
                            placeholder = { Text(text = "Search") },
                            modifier = Modifier
                                .padding(start = 15.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = {
                                focusManager.clearFocus()
                            }),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    } else {
                        Text(
                            text = "Choose a country",
                            modifier = Modifier
                                .padding(start = 15.dp)
                        )
                    }
                },
                actions = {
                    if (!isSearchNow)
                        Icon(
                            painterResource(R.drawable.magnify),
                            contentDescription = "magnify",
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .clickable { isSearchNow = true }
                        )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            content = {
                items(phoneCodes().items.filter { it.countryName.contains(searchCountry, ignoreCase = true) }) { phoneCode ->
                    ConstraintLayout(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .clickable {
                                focusManager.clearFocus()
                                viewModel.selectCountry(navController,
                                    phoneCode,
                                    NavRoute.PhoneScreen.route)
                            }
                    ) {
                        val (countryFlagText, countryText, codeText) = createRefs()

                        Text(
                            text = phoneCode.region.toEmojiFlag(),
                            modifier = Modifier
                                .constrainAs(countryFlagText) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start, 20.dp)
                                    bottom.linkTo(parent.bottom)
                                }
                        )

                        Text(
                            text = phoneCode.countryName,
                            modifier = Modifier
                                .constrainAs(countryText) {
                                    top.linkTo(parent.top)
                                    start.linkTo(countryFlagText.end, 10.dp)
                                    bottom.linkTo(parent.bottom)
                                }
                        )

                        Text(
                            text = "+${phoneCode.code}",
                            modifier = Modifier
                                .constrainAs(codeText) {
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end, 20.dp)
                                    bottom.linkTo(parent.bottom)
                                }
                        )
                    }
                }

            }
        )
    }
}