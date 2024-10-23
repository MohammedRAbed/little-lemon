package com.example.littlelemon

interface Destination {
    val route: String
}

object OnBoarding : Destination {
    override val route: String = "OnBoardingScreen"
}

object Home : Destination {
    override val route: String = "HomeScreen"
}

object Profile : Destination {
    override val route: String = "ProfileScreen"
}