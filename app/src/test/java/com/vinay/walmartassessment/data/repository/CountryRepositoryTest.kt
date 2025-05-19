package com.vinay.walmartassessment.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.vinay.walmartassessment.data.dto.CountryResponse
import com.vinay.walmartassessment.data.dto.Currency
import com.vinay.walmartassessment.data.dto.Language
import com.vinay.walmartassessment.data.mapper.CountryMapper
import com.vinay.walmartassessment.data.mapper.IMapper
import com.vinay.walmartassessment.data.remote.ApiService
import com.vinay.walmartassessment.domain.data.Country
import com.vinay.walmartassessment.state.ApiStates
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class CountryRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: CountryRepository
    private val apiService: ApiService = mockk()
    private val mapper: CountryMapper = mockk()

    @Before
    fun setUp() {
        repository = CountryRepository(apiService = apiService, iMapper = mapper)
    }

    @Test
    fun `getCountryDetails should emit Loading and then Success when API call is successful`() =
        runTest {
            // Arrange
            val countryResponse = CountryResponse(
                name = "India",
                capital = "New Delhi",
                code = "IN",
                currency = Currency("INR", "Indian rupee", "₹"),
                demonym = "Indian",
                flag = "https://restcountries.eu/data/ind.svg",
                language = Language("hi", "hin", "Hindi", "Hindi"),
                region = "AS"
            )
            val expectedCountryDetails = Country(
                countryName = "India",
                countryRegion = "AS",
                countryCode = "IN",
                countryCapital = "New Delhi"
            )

            coEvery { apiService.getCountries() } returns Response.success(listOf(countryResponse))
            every { mapper.mapEntityToDomain(countryResponse) } returns expectedCountryDetails

            repository.getCountry().test {
                assertTrue(awaitItem() is ApiStates.Loading)
                assertEquals(
                    ApiStates.Success(listOf(expectedCountryDetails)),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun `getCountryDetails should emit Failure when API call is unsuccessful`() = runTest {
        // Arrange
        coEvery { apiService.getCountries() } returns Response.error(
            500,
            "Server Error".toResponseBody()
        )
        repository.getCountry().test {
            assertTrue(awaitItem() is ApiStates.Loading)
            assertEquals(
                ApiStates.Failure("Something Went Wrong. Please try again later..."),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCountryDetails should emit Failure when API response body is null`() = runTest {
        coEvery { apiService.getCountries() } returns Response.success(null)

        repository.getCountry().test {
            assertTrue(awaitItem() is ApiStates.Loading)
            assertEquals(
                ApiStates.Failure("Something Went Wrong. Please try again later..."),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCountryDetails should emit Failure when there is a network issue`() = runTest {
        coEvery { apiService.getCountries() } throws UnknownHostException("Unable to connect. Please check your internet connection and try again.")
        repository.getCountry().test {
            assertTrue(awaitItem() is ApiStates.Loading)
            assertEquals(
                ApiStates.Failure("Unable to connect. Please check your internet connection and try again."),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

}