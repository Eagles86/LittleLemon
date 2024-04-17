package com.example.littlelemonfinal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemonfinal.data.HomeViewModel
import com.example.littlelemonfinal.data.MenuItemRoom

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DishDetails(id: Int) {
    val viewModel: HomeViewModel = viewModel()
    var dish by remember {
        mutableStateOf(MenuItemRoom(0,"","",0.0,"",""))
    }

    LaunchedEffect(true)
    {
        dish =   requireNotNull(viewModel.getDish(id))
    }


    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        TopAppBar()
        GlideImage(
            model = dish.imageUrl,
            contentDescription = "",
            Modifier.fillMaxWidth()
                .fillMaxHeight(0.3f),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(
                start = 10.dp,
                end = 10.dp
            )
        ) {
            Text(
                text = dish.title,
                style = MaterialTheme.typography.h2
            )
            Text(
                text = dish.description,
                style = MaterialTheme.typography.body1
            )
            Counter()
            Button(
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "Add" + " ${dish.price}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
fun Counter() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        var counter by remember {
            mutableStateOf(1)
        }
        TextButton(
            onClick = {
                counter--
            }
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.h2
            )
        }
        Text(
            text = counter.toString(),
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(16.dp)
        )
        TextButton(
            onClick = {
                counter++
            }
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.h2
            )
        }
    }
}


