package com.vinay.walmartassessment.data.repository

import com.vinay.walmartassessment.data.Utils.CONNECTION_ERROR_MSG
import com.vinay.walmartassessment.data.Utils.GENERIC_ERROR_MSG
import com.vinay.walmartassessment.data.dto.CountryResponse
import com.vinay.walmartassessment.data.mapper.CountryMapper
import com.vinay.walmartassessment.data.mapper.IMapper
import com.vinay.walmartassessment.data.remote.ApiService
import com.vinay.walmartassessment.domain.data.Country
import com.vinay.walmartassessment.domain.repository.ICountryRepository
import com.vinay.walmartassessment.state.ApiStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.UnknownHostException

/*
*  This class is used to fetch the Response from Api
*  And we emit the Appropriate state(Loading, Success, Failure)
*  Also use Mapper to Transform the Data into required Data classes
* */
class CountryRepository(
    val iMapper: IMapper<CountryResponse, Country>,
    val apiService: ApiService
): ICountryRepository {
    override suspend fun getCountry(): Flow<ApiStates<List<Country>>> = flow{

        try {
            emit(ApiStates.Loading)
            val response = apiService.getCountries()
            if(!response.isSuccessful){
                emit(ApiStates.Failure(GENERIC_ERROR_MSG))
            }
            else{
                if(response.body()!=null){
                    val countries = response.body()?.map{iMapper.mapEntityToDomain(it)}
                    emit(ApiStates.Success(countries!!))
                }
                else{
                    emit(ApiStates.Failure(GENERIC_ERROR_MSG))
                }
            }
        }
        catch (e: UnknownHostException) {
            emit(ApiStates.Failure(CONNECTION_ERROR_MSG))
        }
        catch (e: Exception){
            emit(ApiStates.Failure(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}