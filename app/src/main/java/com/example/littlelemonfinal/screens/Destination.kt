package com.example.littlelemonfinal.screens

interface Destinations {
    val route: String
}

object Onboarding : Destinations {
    override val route = "Onboarding"
}

object Home : Destinations {
    override val route = "Home"
}

object Profile : Destinations {
    override val route = "Profile"
}

object Details : Destinations {
    override val route = "Details"
    const val argDishId = "dishId"
}