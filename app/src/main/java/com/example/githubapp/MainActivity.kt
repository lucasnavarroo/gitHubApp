package com.example.githubapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.theme.GitHubAppTheme
import com.example.githubapp.di.appModules
import com.example.users_presentation.details.UserDetailsScreen
import com.example.users_presentation.details.UserDetailsViewModel
import com.example.users_presentation.list.view.UsersScreen
import com.example.users_presentation.list.viewmodel.UsersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalContext.startKoin {
            androidContext(this@MainActivity)
            modules(appModules)
        }

        val usersViewModel: UsersViewModel by viewModel()
        val userDetailsViewModel: UserDetailsViewModel by viewModel()

        setContent {
            GitHubAppTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.USERS
                    ) {
                        composable(Route.USERS) {
                            UsersScreen(
                                onDetailsClick = { login ->
                                    navController.navigate(
                                        Route.USER_DETAILS.replace("{login}", login)
                                    )
                                },
                                scaffoldState = scaffoldState,
                                viewModel = usersViewModel,
                            )
                        }
                        composable(Route.USER_DETAILS) { backStackEntry ->
                            val login = backStackEntry.arguments?.getString("login")
                            UserDetailsScreen(
                                login = login ?: "",
                                scaffoldState = scaffoldState,
                                viewModel = userDetailsViewModel,
                            ) {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}