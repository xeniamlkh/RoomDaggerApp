package com.example.roomdaggerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.roomdaggerapp.ui.theme.RoomDaggerAppTheme
import com.example.database.domain.DatabaseEntryPoint
import com.example.network.domain.NetworkEntryPoint
import com.example.roomdaggerapp.mainscreen.MainActivityScreen
import com.example.roomdaggerapp.mainscreen.MainActivityViewModel
import com.example.roomdaggerapp.mainscreen.MainActivityViewModelFactory
import com.example.roomdaggerapp.repo.MainActivityRepositoryImpl

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            MainActivityRepositoryImpl(
                dictionaryRepository = DatabaseEntryPoint.getDictionaryRepository(),
                networkApi = NetworkEntryPoint.getNetworkApi()
            )

        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomDaggerAppTheme {
                MainActivityScreen(viewModel)
            }
        }
    }
}