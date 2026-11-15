package com.example.welcome.navigation

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.welcome.di.WelcomeComponent
import com.example.welcome.ui.WelcomeScreen
import com.example.welcome.ui.WelcomeViewModel

const val WELCOME_ROUTE = "welcome"

fun NavGraphBuilder.welcomeScreen(
    componentFactory: WelcomeComponent.Factory,
    onAddWord: () -> Unit
) {
    composable(WELCOME_ROUTE) {
        val component = remember {
            componentFactory.create()
        }
        val viewModel: WelcomeViewModel = viewModel(
            factory = component.welcomeViewModelFactory()
        )
        WelcomeScreen(viewModel = viewModel, onAddWord = onAddWord)
    }
}