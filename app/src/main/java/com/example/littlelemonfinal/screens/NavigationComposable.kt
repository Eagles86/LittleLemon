package com.example.littlelemonfinal.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun NavigationComposable(context: Context, navController: NavHostController) {

    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    var start = Onboarding.route
    if (sharedPreferences.getBoolean("userRegistered", false)) {
        start = Home.route
    }

    NavHost(navController = navController, startDestination = Home.route) {
        composable(Onboarding.route) {
            Onboarding(context, navController)
        }
        composable(Profile.route) {
            Profile(context, navController)
        }
        composable(Home.route) {
            Home(navController)
        }

        composable( Details.route + "/{${Details.argDishId}}",
            arguments = listOf(navArgument(Details.argDishId) { type = NavType.IntType })
        ) {
            val id = requireNotNull(it.arguments?.getInt(Details.argDishId)) { "Dish id is null" }
            DishDetails(id)
        }
    }
}

