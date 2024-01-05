package com.example.users_presentation.di

import com.example.users_domain.usecase.GetRepositoriesUseCase
import com.example.users_domain.usecase.GetUsersUseCase
import com.example.users_domain.usecase.SearchUserUseCase
import com.example.users_presentation.details.UserDetailsViewModel
import com.example.users_presentation.list.viewmodel.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { provideUsersViewModel(get(), get()) }
    viewModel { provideUserDetailsViewModel(get(), get()) }
}

fun provideUsersViewModel(
    getUsersUseCase: GetUsersUseCase,
    searchUserUseCase: SearchUserUseCase
): UsersViewModel {
    return UsersViewModel(
        getUsersUseCase = getUsersUseCase,
        searchUserUseCase = searchUserUseCase
    )
}

fun provideUserDetailsViewModel(
    searchUserUseCase: SearchUserUseCase,
    getRepositoriesUseCase: GetRepositoriesUseCase
): UserDetailsViewModel {
    return UserDetailsViewModel(
        searchUserUseCase = searchUserUseCase,
        getRepositoriesUseCase = getRepositoriesUseCase
    )
}

