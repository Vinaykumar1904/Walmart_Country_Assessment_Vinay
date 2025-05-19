package com.vinay.walmartassessment.data.mapper

interface IMapper<E,D> {

    /*
     This Function Helps Us map Entity dto to Domain data classes
     use when we want to get only Required amount of data to complete the use case
     */
    fun mapEntityToDomain(countryResponse:E):D
}