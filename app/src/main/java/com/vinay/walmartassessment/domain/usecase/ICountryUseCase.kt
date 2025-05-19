package com.vinay.walmartassessment.domain.usecase

import com.vinay.walmartassessment.domain.data.Country
import com.vinay.walmartassessment.state.ApiStates
import kotlinx.coroutines.flow.Flow

interface ICountryUseCase {
    suspend fun invoke():Flow<ApiStates<List<Country>>>
}