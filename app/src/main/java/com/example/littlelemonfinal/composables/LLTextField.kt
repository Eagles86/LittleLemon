package com.example.littlelemonfinal.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.littlelemonfinal.R

@Composable
fun LLTextField(text: String, label: String, placeHolder: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(text = label) },
        singleLine = true,
        placeholder = { Text(text = placeHolder) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = MaterialTheme.colors.onSurface,
            focusedBorderColor = MaterialTheme.colors.error
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun LLInactiveTextField(text: String, label: String, placeHolder: String) {
    OutlinedTextField(
        enabled = false,
        readOnly = true,
        value = text,
        onValueChange = {},
        label = { Text(text = label) },
        singleLine = true,
        placeholder = { Text(text = placeHolder) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = MaterialTheme.colors.onSurface,
            disabledLabelColor = MaterialTheme.colors.error
        ),
        modifier = Modifier.fillMaxWidth()
    )
}