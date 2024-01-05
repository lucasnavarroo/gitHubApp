package com.example.users_domain.di

import com.example.users_domain.repository.UsersRepository
import com.example.users_domain.usecase.GetRepositoriesUseCase
import com.example.users_domain.usecase.GetUsersUseCase
import com.example.users_domain.usecase.SearchUserUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single { provideGetUsersUseCase(get()) }
    single { provideSearchUserUseCase(get()) }
    single { provideUserRepositoriesUseCase(get()) }
}

fun provideGetUsersUseCase(usersRepository: UsersRepository): GetUsersUseCase {
    return GetUsersUseCase(
        repository = usersRepository
    )
}

fun provideSearchUserUseCase(usersRepository: UsersRepository): SearchUserUseCase {
    return SearchUserUseCase(
        repository = usersRepository
    )
}

fun provideUserRepositoriesUseCase(usersRepository: UsersRepository): GetRepositoriesUseCase {
    return GetRepositoriesUseCase(
        repository = usersRepository
    )
}