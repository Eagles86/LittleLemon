package com.example.littlelemonfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.navigation.compose.rememberNavController
import com.example.littlelemonfinal.ui.theme.LittleLemonTheme
import com.example.littlelemonfinal.screens.NavigationComposable
import com.example.littlelemonfinal.screens.Onboarding

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()
                NavigationComposable(context = applicationContext, navController = navController)
            }
        }
    }
}