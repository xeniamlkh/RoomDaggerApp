package com.example.addword.navigation

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.addword.di.AddWordDependencies
import com.example.addword.di.DaggerAddWordComponent
import com.example.addword.ui.AddWordScreen
import com.example.addword.ui.AddWordViewModel

const val ADD_WORD_ROUTE = "add_word"

fun NavGraphBuilder.addWordScreen(
    dependencies: AddWordDependencies,
    onBack: () -> Unit
) {
    composable(ADD_WORD_ROUTE) {
        val component = remember {
            DaggerAddWordComponent.factory().create(dependencies)
        }
        val viewModel: AddWordViewModel = viewModel(
            factory = component.addWordViewModelFactory()
        )
        AddWordScreen(viewModel = viewModel, onBack = onBack)
    }
}