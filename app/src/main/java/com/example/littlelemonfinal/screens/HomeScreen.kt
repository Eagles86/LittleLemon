package com.example.littlelemonfinal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemonfinal.R
import com.example.littlelemonfinal.data.HomeViewModel
import com.example.littlelemonfinal.data.MenuItemRoom

@Composable
fun Home(navController: NavHostController) {

    val viewModel: HomeViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.netWorkFetch()
    }

    Column() {
        Header(navController)
        TopSection(viewModel = viewModel)
        MenuSection(viewModel,navController)
    }
}

@Composable
fun Header(navController: NavHostController) {
    val viewModel: HomeViewModel = viewModel()
    val isLoggedIn = viewModel.isLogedIn
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.width(50.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(0.65f)
        )
        Box(modifier = Modifier
            .size(50.dp)
            .clickable {
                if (isLoggedIn.value == true) {
                    navController.navigate(Profile.route)
                } else {
                    navController.navigate(Onboarding.route) {
                        popUpTo(Home.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Icon",
                tint = MaterialTheme.colors.background,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 2.dp)
            )
        }
    }
}

@Composable
fun TopSection(viewModel: HomeViewModel) {
    Column(
        modifier = Modifier
            .background(Color.DarkGray)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onPrimary
        )
        Text(text = stringResource(id = R.string.location), style = MaterialTheme.typography.h5, color = Color.White)
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.description),
                modifier = Modifier.fillMaxWidth(0.7f),
                color = Color.White,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.FillBounds
            )
        }

        val searchPhrase = viewModel.searchPhrase.observeAsState("").value

        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = searchPhrase,
            onValueChange = {
                viewModel.searchPhrase.value = it
                viewModel.fetchMenuItems()
            },
            placeholder = {
                Text(text = stringResource(id = R.string.enter_search_phrase))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colors.background
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MenuSection(viewModel: HomeViewModel,navController: NavHostController) {

    val databaseMenuItems = viewModel.menuItems.observeAsState(emptyList()).value
    val categories = viewModel.categories.observeAsState(emptyList()).value

    Column {
        MenuCategories(categories) {
            viewModel.category.value = it
            viewModel.fetchMenuItems()
        }

        Spacer(modifier = Modifier.size(2.dp))
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        for (item in databaseMenuItems) {
            MenuItem(item = item,navController)
            Divider(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                thickness = 1.dp,
                color = Color.Yellow
            )
        }
    }
}

@Composable
fun MenuCategories(categories: List<String>, categoryLambda: (sel: String) -> Unit) {

    val cat = remember {
        mutableStateOf("")
    }

    Column(Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
        Text(text = stringResource(id = R.string.order_for_delivery), fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (category in categories) {
                CategoryButton(category = category, category == cat.value) {
                    if (cat.value == it) {
                        categoryLambda("")
                    } else {
                        categoryLambda(it)
                    }
                    cat.value = it
                }
            }
        }
    }
}

@Composable
fun CategoryButton(category: String, isClicked: Boolean, selectedCategory: (sel: String) -> Unit) {

    val buttonColor = if (isClicked) Color.Gray else Color(red = 237, green = 239, blue = 238)

    Button(
        modifier = Modifier.clip(RoundedCornerShape(30.dp)),
        onClick = {
            val selCat = if (isClicked) "" else category
            selectedCategory(selCat)
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            backgroundColor = buttonColor
        )
    ) {
        Text(text = category)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemRoom,navController: NavHostController) {

    val itemDescription = item.description.menuTitle
    Card( Modifier
        .fillMaxWidth()
        .padding(horizontal = 5.dp, vertical = 5.dp)
        .clickable {
            navController.navigate(Details.route + "/${item.id}") }
        .clickable {
            navController.navigate(Details.route + "/${item.id}") })
    {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.fillMaxWidth(0.7f)
                ,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(text = itemDescription, modifier = Modifier.padding(bottom = 10.dp))
                Text(text = "$ ${item.price}", fontWeight = FontWeight.Bold)
            }

            GlideImage(
                model = item.imageUrl,
                contentDescription = "",
                Modifier.size(100.dp, 100.dp),
                contentScale = ContentScale.Crop
            )
        }
    }

}

val String.menuTitle: String
    get() {
        val itemDescription = if (this.length > 100) {
            this.substring(0, 100) + ". . ."
        } else {
            this
        }

        return itemDescription
    }