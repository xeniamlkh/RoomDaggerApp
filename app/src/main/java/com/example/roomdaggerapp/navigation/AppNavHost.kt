package com.example.roomdaggerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.addword.navigation.ADD_WORD_ROUTE
import com.example.addword.navigation.addWordScreen
import com.example.roomdaggerapp.di.MainActivityComponent
import com.example.welcome.navigation.WELCOME_ROUTE
import com.example.welcome.navigation.welcomeScreen

@Composable
fun AppNavHost(activityComponent: MainActivityComponent) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WELCOME_ROUTE) {
        welcomeScreen(
            componentFactory = activityComponent.welcomeComponentFactory(),
            onAddWord = {
                navController.navigate(ADD_WORD_ROUTE)
            }
        )
        addWordScreen(
            dependencies = activityComponent,
            onBack = { navController.popBackStack() }
        )
    }
}