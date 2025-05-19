package com.vinay.walmartassessment.domain.usecase

import com.vinay.walmartassessment.domain.data.Country
import com.vinay.walmartassessment.domain.repository.ICountryRepository
import com.vinay.walmartassessment.state.ApiStates
import kotlinx.coroutines.flow.Flow

class CountryUseCase(val iCountryRepository: ICountryRepository): ICountryUseCase {

    /*
      Fetching Country Details from the Repository(Single Source of Truth for Data)
    * */
    override suspend fun invoke(): Flow<ApiStates<List<Country>>> {
        return  iCountryRepository.getCountry()
    }
}