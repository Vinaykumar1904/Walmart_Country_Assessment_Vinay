package com.vinay.walmartassessment.domain.repository

import com.vinay.walmartassessment.domain.data.Country
import com.vinay.walmartassessment.state.ApiStates
import kotlinx.coroutines.flow.Flow

interface ICountryRepository {
    suspend fun getCountry(): Flow<ApiStates<List<Country>>>
}