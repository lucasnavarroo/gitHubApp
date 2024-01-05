package com.example.users_domain

import com.example.users_domain.model.Repository
import com.example.users_domain.repository.UsersRepository
import com.example.users_domain.usecase.GetRepositoriesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetRepositoriesUseCaseTest {
    private lateinit var getRepositoriesUseCase: GetRepositoriesUseCase
    private val repository = mockk<UsersRepository>(relaxed = true)

    @Before
    fun setUp() {
        getRepositoriesUseCase = GetRepositoriesUseCase(repository)
    }

    @Test
    fun `it should get repositories from repository successfully`() = runBlocking {
        val response: Result<List<Repository>> = Result.success(emptyList())
        coEvery { repository.getUserRepositories(any()) } returns response

        val getRepos = getRepositoriesUseCase.invoke("")

        Assert.assertEquals(getRepos, Result.success(emptyList<Repository>()))
    }
}