package com.vinay.walmartassessment.data.mapper

import com.vinay.walmartassessment.data.dto.CountryResponse
import com.vinay.walmartassessment.domain.data.Country

/*
* This Class Helps Us map Entity dto to Domain data classes
*  use when we want to get only Required amount of data to complete the use case
* */
class CountryMapper: IMapper<CountryResponse, Country> {
    override fun mapEntityToDomain(countryResponse: CountryResponse): Country {
        return Country(
            countryName = countryResponse.name,
            countryRegion = countryResponse.region,
            countryCode = countryResponse.code,
            countryCapital = countryResponse.capital
        )
    }
}