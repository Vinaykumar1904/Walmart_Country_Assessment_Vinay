package com.vinay.walmartassessment.domain.usecase

import com.vinay.walmartassessment.domain.data.Country
import com.vinay.walmartassessment.domain.repository.ICountryRepository
import com.vinay.walmartassessment.state.ApiStates
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/*
This Class Provides Testcases for FetchCountryDetailsUseCase class
*/
@ExperimentalCoroutinesApi
class FetchCountryDetailsUseCaseTest {
    private lateinit var countryUseCase: CountryUseCase
    private val iCountryRepository: ICountryRepository = mockk()

    @Before
    fun setUp() {
        countryUseCase = CountryUseCase(iCountryRepository)
    }

    @Test
    fun `invoke returns Success when repository emits data`() = runTest {
        val mockData = listOf(Country("India", "AS", "IN", "New Delhi"))
        val flow = flowOf(ApiStates.Success(mockData))

        coEvery { iCountryRepository.getCountry() } returns flow

        val result = countryUseCase.invoke().first()

        assertTrue(result is ApiStates.Success)
        assertEquals(mockData, (result as ApiStates.Success).data)
    }

    @Test
    fun `invoke returns Failure when repository throws an exception`() = runTest {
        val errorMessage = "Network Error"
        val flow: Flow<ApiStates<List<Country>>> =
            flowOf(ApiStates.Failure(errorMessage))

        coEvery { iCountryRepository.getCountry() } returns flow

        val result = countryUseCase.invoke().first()

        assertTrue(result is ApiStates.Failure)
        assertEquals(errorMessage, (result as ApiStates.Failure).msg)
    }
}