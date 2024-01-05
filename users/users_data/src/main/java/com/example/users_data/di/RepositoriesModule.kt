package com.example.users_data.di

import com.example.users_data.remote.GitHubAPI
import com.example.users_data.repository.UsersRepositoryImpl
import com.example.users_domain.repository.UsersRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single<UsersRepository> { provideUsersRepository(get()) }
}

fun provideUsersRepository(gitHubApi: GitHubAPI): UsersRepositoryImpl {
    return UsersRepositoryImpl(
        api = gitHubApi
    )
}
