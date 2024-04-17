package com.example.littlelemonfinal.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemonfinal.R
import com.example.littlelemonfinal.composables.LLTextField

@Composable
fun Onboarding(context: Context, navHostController: NavHostController) {

    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)

    val firstName = remember {
        mutableStateOf("")
    }

    val lastName = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .fillMaxWidth(0.6f)
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = "Little Lemon Logo"
            )
        }

        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            Spacer(modifier = Modifier.size(30.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.lets_get_to),
                    color = Color.White,
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.size(30.dp))
        }

        Column(
            Modifier
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.personal_info),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                style = MaterialTheme.typography.h5
            )

            LLTextField(
                text = firstName.value,
                label = stringResource(id = R.string.first_name),
                placeHolder = stringResource(id = R.string.first_name_placeholder)
            ) {
                firstName.value = it
            }

            Spacer(modifier = Modifier.size(20.dp))

            LLTextField(
                text = lastName.value,
                label = stringResource(id = R.string.last_name),
                placeHolder = stringResource(id = R.string.last_name_placeholder)
            ) {
                lastName.value = it
            }

            Spacer(modifier = Modifier.size(20.dp))

            LLTextField(
                text = email.value,
                label = stringResource(id = R.string.email),
                placeHolder = stringResource(id = R.string.email_placeholder)
            ) {
                email.value = it
            }

            Spacer(modifier = Modifier.size(40.dp))

            Button(
                onClick = {
                    if (validateRegistrationData(
                            firstName.value,
                            lastName.value,
                            email.value
                        )
                    ) {
                        sharedPreferences.edit()
                            .putString("firstName", firstName.value)
                            .putString("lastName", lastName.value)
                            .putString("email", email.value)
                            .apply()

                        navHostController.navigate(Home.route) {
                            popUpTo(Onboarding.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colors.primary
                )
            ) {
                Text(text = stringResource(id = R.string.register))
            }
        }
    }
}

fun validateRegistrationData(firstName: String, lastName: String, email: String): Boolean {
    var validated = false

    if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            validated = true
    }

    return validated
}