package com.example.roomdaggerapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.addword.di.AddWordDependencies
import com.example.addword.navigation.AddWordHost
import com.example.roomdaggerapp.di.MainActivityComponent
import com.example.welcome.di.WelcomeComponent
import com.example.welcome.navigation.WelcomeHost

class MainActivity : AppCompatActivity(), WelcomeHost, AddWordHost {

    private val activityComponent: MainActivityComponent by lazy {
        (application as App).appComponent
            .mainActivityComponentFactory()
            .create(this)
    }

    private val navController: NavController
        get() = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment).navController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    }

    override fun welcomeComponentFactory(): WelcomeComponent.Factory =
        activityComponent.welcomeComponentFactory()

    override fun openAddWord() {
        if (navController.currentDestination?.id == R.id.welcomeFragment) {
            navController.navigate(R.id.action_welcome_to_addWord)
        }
    }

    override fun addWordDependencies(): AddWordDependencies = activityComponent
}