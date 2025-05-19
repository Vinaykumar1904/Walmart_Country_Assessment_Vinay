package com.vinay.walmartassessment.presentation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vinay.walmartassessment.domain.data.Country
import com.vinay.walmartassessment.domain.usecase.CountryUseCase
import com.vinay.walmartassessment.state.ApiStates
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Rule

/*
* This Class Provides Testcases for CountryViewModel
* */

@OptIn(ExperimentalCoroutinesApi::class)
class CountryViewModelTest {
    private lateinit var viewModel: CountryViewModel
    private lateinit var iCountryUseCase: CountryUseCase
    private lateinit var testDispatcher: TestDispatcher
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        iCountryUseCase= mockk()
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }
    @Test
    fun `fetchCountryDetails update countryStateFlow successfully`() = runTest(testDispatcher) {
        val fakeDomainList = listOf(
            Country("India", "AS", "IN", "New Delhi")
        )
        val fakeSuccessFlow = flow {
            emit(ApiStates.Success(fakeDomainList))
        }
        coEvery { iCountryUseCase.invoke() } returns fakeSuccessFlow

        viewModel = CountryViewModel(iCountryUseCase)
        viewModel.getCountries()
        coVerify{iCountryUseCase.invoke()}
        val stateList = mutableListOf<ApiStates<List<Country>>>()
        val job = launch {
            viewModel.uiState.collect{
                stateList.add(it)
            }
        }
        advanceUntilIdle()
        job.cancel()

        assert(stateList[0] is ApiStates.Success)
        val successResult = stateList[0] as ApiStates.Success
        assert(successResult.data == fakeDomainList)
    }

    @Test
    fun `fetchCountryDetails failed due to api issue`() = runTest(testDispatcher) {
        val errorMessage = "Network Error"
        val fakeErrorFlow = flow {
            emit(ApiStates.Failure(errorMessage))
        }
        coEvery { iCountryUseCase.invoke() } returns fakeErrorFlow

        viewModel = CountryViewModel(iCountryUseCase)
        viewModel.getCountries()
        coVerify { iCountryUseCase.invoke() }
        val uiStates = mutableListOf<ApiStates<List<Country>>>()
        val job = launch {
            viewModel.uiState.collect {
                uiStates.add(it)
            }
        }
        advanceUntilIdle()
        job.cancel()
        assert(uiStates[0] is ApiStates.Failure)
        val errorResult = uiStates[0] as ApiStates.Failure
        assert(errorResult.msg == errorMessage)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}