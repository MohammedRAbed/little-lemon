package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.MainActivity.Companion.LOGGED_KEY
import com.example.littlelemon.MainActivity.Companion.PREFS_KEY

@Composable
fun LittleLemonNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val isLogged = sharedPreferences.getBoolean(LOGGED_KEY, false)

    Box(modifier = modifier) {
        NavHost(navController = navController, startDestination = if (isLogged) Home.route else OnBoarding.route) {
            composable(Home.route) { HomeScreen(navController) }
            composable(Profile.route) { ProfileScreen(navController) }
            composable(OnBoarding.route) { OnBoardingScreen(navController) }
        }
    }
}