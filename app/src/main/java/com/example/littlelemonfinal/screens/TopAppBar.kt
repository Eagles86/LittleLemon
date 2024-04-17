package com.example.littlelemonfinal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit

import androidx.compose.ui.unit.dp
import com.example.littlelemonfinal.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopAppBar(scaffoldState: ScaffoldState? = null, scope: CoroutineScope? = null) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            scope?.launch { scaffoldState?.drawerState?.open() }
        }) {
            Image(
                painter = painterResource(id = R.drawable.ic_hamburger_menu),
                contentDescription = "Menu Icon",
                modifier = Modifier.size(24.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(0.5F)
                .padding(horizontal = 20.dp)
        )
        IconButton(onClick = { }) {
            Image(
                painter = painterResource(id = R.drawable.ic_cart ),
                contentDescription = "Cart",

                modifier = Modifier.size(24.dp)
            )
            Text(text = "12", color = Color.Red)
        }
    }
}


