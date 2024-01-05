package com.example.users_domain

import com.example.users_domain.model.User
import com.example.users_domain.repository.UsersRepository
import com.example.users_domain.usecase.GetUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {

    private lateinit var getUsersUseCase: GetUsersUseCase
    private val repository = mockk<UsersRepository>(relaxed = true)

    @Before
    fun setUp() {
        getUsersUseCase = GetUsersUseCase(repository)
    }

    @Test
    fun `it should get users from repository successfully`() = runBlocking {
        val response: Result<List<User>> = Result.success(emptyList())
        coEvery { repository.getUsers() } returns response

        val getUsers = getUsersUseCase.invoke()

        Assert.assertEquals(getUsers, Result.success(emptyList<User>()))
    }
}