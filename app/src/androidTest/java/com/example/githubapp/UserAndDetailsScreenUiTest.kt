package com.example.githubapp

import android.annotation.SuppressLint
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.example.core.theme.GitHubAppTheme
import com.example.users_domain.model.User
import com.example.users_domain.usecase.GetRepositoriesUseCase
import com.example.users_domain.usecase.GetUsersUseCase
import com.example.users_domain.usecase.SearchUserUseCase
import com.example.users_presentation.details.UserDetailsScreen
import com.example.users_presentation.details.UserDetailsViewModel
import com.example.users_presentation.list.view.UsersScreen
import com.example.users_presentation.list.viewmodel.UsersViewModel
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalComposeUiApi
@ExperimentalCoilApi
class UserAndDetailsScreenUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var usersRepositoryFake: UsersRepositoryFake
    private lateinit var usersUseCase: GetUsersUseCase
    private lateinit var searchUserUseCase: SearchUserUseCase
    private lateinit var getRepositoriesUseCase: GetRepositoriesUseCase
    private lateinit var usersViewModel: UsersViewModel
    private lateinit var userDetailsViewModel: UserDetailsViewModel
    private lateinit var navController: NavHostController

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Before
    fun setUp() {
        usersRepositoryFake = UsersRepositoryFake()
        usersUseCase = GetUsersUseCase(usersRepositoryFake)
        searchUserUseCase = SearchUserUseCase(usersRepositoryFake)
        getRepositoriesUseCase = GetRepositoriesUseCase(usersRepositoryFake)
        usersViewModel = UsersViewModel(usersUseCase, searchUserUseCase)
        userDetailsViewModel = UserDetailsViewModel(searchUserUseCase, getRepositoriesUseCase)

        composeRule.activity.setContent {
            GitHubAppTheme {
                navController = rememberNavController()
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

    @Test
    fun performUiTests() {
        usersRepositoryFake.usersResult = listOf(
            User(
                "lucas",
                "profile",
                "profileUrl",
                "lucas",
                "google",
                "sp",
                "email",
                "bio",
                1
            )
        )

        composeRule
            .onNodeWithText("Usuários GitHub")
            .assertExists()
            .assertIsDisplayed()

        composeRule
            .onNodeWithTag("user_item")
            .assertExists()
            .assertIsDisplayed()

        composeRule
            .onNodeWithTag("loading_box")
            .assertExists()
            .assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("lucas")
            .assertIsDisplayed()

        assertThat(
            navController
                .currentDestination
                ?.route
                ?.startsWith(Route.USERS)
        ).isTrue()

        composeRule
            .onNodeWithContentDescription("lucas")
            .performClick()

        assertThat(
            navController
                .currentDestination
                ?.route
                ?.startsWith(Route.USER_DETAILS)
        ).isTrue()

        composeRule
            .onNodeWithTag("user_details_screen")
            .assertExists()
            .assertIsDisplayed()

        composeRule
            .onNodeWithTag("icon_back")
            .assertExists()
            .assertIsDisplayed()

        composeRule
            .onNodeWithTag("icon_back")
            .performClick()

        assertThat(
            navController
                .currentDestination
                ?.route
                ?.startsWith(Route.USERS)
        ).isTrue()
    }
}