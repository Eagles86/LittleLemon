package com.example.littlelemonfinal.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.littlelemonfinal.R
import com.example.littlelemonfinal.composables.LLInactiveTextField
import com.example.littlelemonfinal.data.HomeViewModel

@Composable
fun Profile(context: Context, navHostController: NavHostController) {
    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)

    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(Modifier.fillMaxWidth(0.6f)) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo"
            )
        }

        Text(
            text = stringResource(id = R.string.personal_info),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h5
        )

        LLInactiveTextField(
            text = sharedPreferences.getString("firstName", "") ?: "",
            label = stringResource(id = R.string.first_name),
            placeHolder = stringResource(id = R.string.first_name_placeholder)
        )

        LLInactiveTextField(
            text = sharedPreferences.getString("lastName", "") ?: "",
            label = stringResource(id = R.string.last_name),
            placeHolder = stringResource(id = R.string.last_name_placeholder)
        )

        LLInactiveTextField(
            text = sharedPreferences.getString("email", "") ?: "",
            label = stringResource(id = R.string.email),
            placeHolder = stringResource(id = R.string.email_placeholder)
        )

        Spacer(modifier = Modifier.size(40.dp))

        Button(
            onClick = {
                sharedPreferences.edit()
                    .clear()
                    .apply()

                navHostController.navigate(Onboarding.route) {
                    popUpTo(Home.route) { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colors.primary
            )
        )
        {
            Text(text = stringResource(id = R.string.log_out))
        }
    }
}