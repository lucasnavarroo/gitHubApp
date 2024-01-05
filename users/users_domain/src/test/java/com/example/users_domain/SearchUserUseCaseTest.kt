package com.example.users_domain

import com.example.users_domain.model.User
import com.example.users_domain.repository.UsersRepository
import com.example.users_domain.usecase.SearchUserUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchUserUseCaseTest {
    private lateinit var searchUserUseCase: SearchUserUseCase
    private val repository = mockk<UsersRepository>(relaxed = true)

    @Before
    fun setUp() {
        searchUserUseCase = SearchUserUseCase(repository)
    }

    @Test
    fun `it should search user from repository successfully`() = runBlocking {
        val user = User(
            "lucas",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            0
        )
        val response: Result<User> = Result.success(user)
        coEvery { repository.searchUser(any()) } returns response

        val searchUser = searchUserUseCase.invoke("")

        Assert.assertEquals(searchUser, Result.success(user))
    }
}